package com.example.demo;
import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
public class WordlookupController {
	@RequestMapping(value="/wordsearch",method=RequestMethod.GET)
	public String wordLookupRequest(@RequestParam("searchtext") String searchword , 
			@RequestParam("searchtype") String searchtype,
			Model model) {
		if (!searchword.equals("")) //non-empty search criteria was sent
		{
			try {
				if (searchtype.equals("beginswith")) //return search results for words that begin with what they specified
				{
					model.addAttribute("wordresults", WordDatabase.SearchForWords(searchword + '%'));
					model.addAttribute("result_text", "Search results beginning with " + searchword);
				}
				else if (searchtype.equals("endswith")) //return search results for words that end with what they specified
				{
					model.addAttribute("wordresults", WordDatabase.SearchForWords('%' + searchword));
					model.addAttribute("result_text", "Search results ending with " + searchword);
				}
				else //return search results for words that contain what they specified
				{
					model.addAttribute("wordresults", WordDatabase.SearchForWords('%' + searchword + '%'));
					model.addAttribute("result_text", "Search results containing " + searchword);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
			model.addAttribute("result_text", "" + searchword);			
		return "index";
	}


}
