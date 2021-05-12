package com.example.demo;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class WordlookupController {
	@RequestMapping(value="/wordsearch",method=RequestMethod.GET)
	public String wordLookupRequest(@RequestParam("searchtext") String searchword, Model model) {
		if (searchword != "")
		{
			try {
				model.addAttribute("wordresults", WordDatabase.SearchForWords('%' + searchword + '%'));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "index";
	}


}
