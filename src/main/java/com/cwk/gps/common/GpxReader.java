package com.cwk.gps.common;

import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Author: LEAVES
 * @Version 1.0
 * @Date: 2021年10月08日  15时26分57秒
 * @Description: 读取GPX文件工具类
 * <p>
 * 参照JAVA读取XML文档的方法读取GPX文件
 */
public class GpxReader {

    //这里时间格式根据GPX文件中的时间格式来选择确定
    private static final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private static final SimpleDateFormat sdf4 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

    private final File gpxFile;

    public GpxReader(String path) {

        this.gpxFile = new File(path);

        if (!this.gpxFile.exists()) {

            throw new RuntimeException("File " + path + "does not exist.");

        } else if (this.gpxFile.isDirectory()) {

            throw new RuntimeException("The given file is a directory.");
        }
    }

    /**
     * 读取轨迹数据
     * <p>
     * 标准的gpx就是下面这些标签名称，如有特殊请自行添加或修改
     *
     * @return
     */
    public Track readData() {
        Track track = new Track();
        try {
            //1、得到 DOM 解析器的工厂实例
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            //2、然后从 DOM 工厂获得 DOM 解析器
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            //3、把要解析的 GPX 文档转化为输入流，以便 DOM 解析器解析它
            Document document = dBuilder.parse(this.gpxFile);
            //4、找出每个字节点（也可以理解其为根节点）
            //获取指定节点
            //需要的数据都在trk标签中
            NodeList trk = document.getElementsByTagName("trk");
            for (int i = 0; i < trk.getLength(); ++i) {
                Node trkItem = trk.item(i);
                if (trkItem.getNodeName().equals("trk")) {
                    NodeList trkSegments = trkItem.getChildNodes();
                    for (int j = 0; j < trkSegments.getLength(); ++j) {
                        Node trkSegment = trkSegments.item(j);
                        if (trkSegment.getNodeName().equals("trkseg")) {
                            TrackSegment segment = new TrackSegment();
                            track.addTrackSegment(segment);
                            //获取其对应字节点
                            NodeList trkPts = trkSegment.getChildNodes();
                            for (int k = 0; k < trkPts.getLength(); ++k) {
                                Node trkPt = trkPts.item(k);
                                String nodename = trkPt.getNodeName();
                                if (trkPt.getNodeName().equals("trkpt")) {
                                    Element element = (Element) trkPt;
                                    double lat = Double.parseDouble(element.getAttribute("lat"));
                                    double lon = Double.parseDouble(element.getAttribute("lon"));
                                    Double ele = null;
                                    String time = null;
                                    //获取其对应字节点
                                    List<Node> nodes = toNodeList(element.getChildNodes());
                                    Optional<Node> elev = nodes.stream().filter((e) -> {
                                        return e.getNodeName().equals("ele");
                                    }).findFirst();
                                    if (elev.isPresent()) {
                                        ele = Double.parseDouble(((Node) elev.get()).getTextContent());
                                    }
                                    Optional<Node> timeNode = nodes.stream().filter((e) -> {
                                        return e.getNodeName().equals("time");
                                    }).findFirst();
                                    if (timeNode.isPresent()) {
                                        time = ((Node) timeNode.get()).getTextContent();
                                    }
                                    segment.addTrackPoint(new TrackPoint(lat, lon, ele, this.parseDate(time)));
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException | ParserConfigurationException | SAXException var26) {
            var26.printStackTrace();
        }

        return track;
    }

    private static List<Node> toNodeList(NodeList nodeList) {
        List<Node> nodes = new ArrayList();

        for (int i = 0; i < nodeList.getLength(); ++i) {
            nodes.add(nodeList.item(i));
        }

        return nodes;
    }

    private Date parseDate(String value) {
        Date date = null;
        try {
            date = sdf1.parse(value);
        } catch (ParseException var5) {

        }
        return date;
    }
}

