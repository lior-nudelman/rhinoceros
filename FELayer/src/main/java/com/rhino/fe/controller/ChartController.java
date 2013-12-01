package com.rhino.fe.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.keypoint.PngEncoder;
import com.rhino.mailParser.data.UserData;
import com.rhino.mailParser.data.UserDataDAO;

@Controller
public class ChartController {

	private SessionFactory sessionFactory;
	
	@Autowired
	public ChartController(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}

	@RequestMapping(value="/graph" , method = RequestMethod.GET)
	public String printHello(ModelMap model) {
		return "graph";
	}
	
	@RequestMapping("/barChart")
	public void getBarChartView(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		System.out.println("In Chart View Controller");

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		dataset.addValue(1.0, "1985-90", "1985-90");

		dataset.addValue(2.5, "1990-95", "1990-95");

		dataset.addValue(4.0, "1995-2000", "1995-2000");

		dataset.addValue(5.0, "2000-05", "2000-05");

		final JFreeChart chart = ChartFactory.createBarChart("Bar Chart",
				"Year", "Population (in millions)", dataset,
				PlotOrientation.VERTICAL, true, true, false);

		chart.setBackgroundPaint(Color.white);
		final TextTitle subtitle = new TextTitle(
				" The below Bar Chart shows population growth in Chennai(India), every 5 years from 1985 .");
		subtitle.setFont(new Font("SansSerif", Font.PLAIN, 12));

		chart.addSubtitle(subtitle);

		final CategoryPlot plot = chart.getCategoryPlot();
		plot.setForegroundAlpha(0.5f);

		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.white);

		final CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
		domainAxis.setLowerMargin(0.0);
		domainAxis.setUpperMargin(0.0);

		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();

		rangeAxis.setLabelAngle(1 * Math.PI / 2.0);

		ChartRenderingInfo info = null;

		info = new ChartRenderingInfo(new StandardEntityCollection());
		response.setContentType("image/png");
		BufferedImage chartImage = chart.createBufferedImage(700, 400, info);

		PngEncoder encoder = new PngEncoder(chartImage, false, 0, 9);

		response.getOutputStream().write(encoder.pngEncode());
	}

	@RequestMapping("/pieChart")
	public void getPieChartView(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("In Chart View Controller");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userID = auth.getName(); // get logged in username

		final DefaultPieDataset dataset = getData("type");

		final JFreeChart chart = ChartFactory.createPieChart3D(userID+"  expense pie",
				dataset, true, true, false);
		chart.setBorderVisible(false);
		chart.setBackgroundPaint(Color.white);
	
		final TextTitle subtitle = new TextTitle(
				userID+"  expense pie");
		subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
		
		chart.addSubtitle(subtitle);
		PiePlot plot = (PiePlot)chart.getPlot();
		plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 14));
		ChartRenderingInfo info = null;

		info = new ChartRenderingInfo(new StandardEntityCollection());
		response.setContentType("image/png");
		BufferedImage chartImage = chart.createBufferedImage(800, 500, info);

		PngEncoder encoder = new PngEncoder(chartImage, false, 0, 9);

		response.getOutputStream().write(encoder.pngEncode());
	}
	
	@RequestMapping("/pieChartFrom")
	public void getPieChartViewFrom(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("In Chart View Controller");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userID = auth.getName(); // get logged in username

		final DefaultPieDataset dataset = getData("from");

		final JFreeChart chart = ChartFactory.createPieChart3D(userID+"  expense pie",
				dataset, true, true, false);
		chart.setBorderVisible(false);
		chart.setBackgroundPaint(Color.white);
	
		final TextTitle subtitle = new TextTitle(
				userID+"  expense pie");
		subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
		
		chart.addSubtitle(subtitle);
		PiePlot plot = (PiePlot)chart.getPlot();
		plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 14));
		ChartRenderingInfo info = null;

		info = new ChartRenderingInfo(new StandardEntityCollection());
		response.setContentType("image/png");
		BufferedImage chartImage = chart.createBufferedImage(800, 500, info);

		PngEncoder encoder = new PngEncoder(chartImage, false, 0, 9);

		response.getOutputStream().write(encoder.pngEncode());
	}

	private DefaultPieDataset getData(String field){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userID = auth.getName(); // get logged in username

		DefaultPieDataset dataset = new DefaultPieDataset();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		tx.begin();
		UserDataDAO userDataDAO = new UserDataDAO();
		userDataDAO.setSession(session);
		List<UserData> events = userDataDAO.getByUserID(userID);
		HashMap<UserData,UserData> data = new HashMap<UserData,UserData>();
		HashMap<String,Float> dataMap = new HashMap<String,Float>();
		for(UserData e: events){
			UserData tmp = data.get(e);
			if(tmp == null){
				data.put(e, e);
				String type = null;
				if(field.equals("type")){
					type = e.getType();
				}
				if(field.equals("from")){
					type = e.getFrom();
				}
				
				if(type == null){
					type ="ME";
				}
				Float tmpVal = dataMap.get(type);
				if(tmpVal == null){
					dataMap.put(type,  e.getAmount());
				}else{
					dataMap.put(type, tmpVal.floatValue() +  e.getAmount());
				}
				
			}else{
				tmp.setDuplicationCounter(tmp.getDuplicationCounter()+1);
			}
			
		}		
		Set<String> keys = dataMap.keySet();
		for(String key:keys){
			dataset.setValue(key, dataMap.get(key));
		}
		tx.commit();
		session.close();
		return dataset;
	}
}