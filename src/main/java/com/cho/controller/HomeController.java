package com.cho.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cho.domain.BoardVO;
import com.cho.domain.Criteria;
import com.cho.domain.PageMaker;
import com.cho.service.BoardService;

@Controller
public class HomeController {
	@Autowired
	private BoardService boardService;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);		
		String formattedDate = dateFormat.format(date);		
		model.addAttribute("serverTime", formattedDate );		
		return "home";
	}
	
	//입력폼으로 가기
	@RequestMapping(value="/main", method=RequestMethod.GET)
	public String inputForm(Criteria cri, Model model) throws Exception{
		
		logger.info(cri.toString());
		
		model.addAttribute("list", boardService.selectBoard(cri));
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(boardService.listCount()); //게시물 총 갯수
				
		model.addAttribute("pageMaker", pageMaker);
		return "main";
	}
	
	@RequestMapping(value="/post")
	public String inputForm2(){	
		return "post";
	}
	
	
	//입력하기	
	@RequestMapping(value="/insert", method=RequestMethod.POST)
	public String insertdao(@RequestParam("title") String title,
							@RequestParam("content") String content,
							@RequestParam("author") String author) throws Exception{
		
		Map<String,String> boardmap = new HashMap<String, String>();
		boardmap.put("title",title);
		boardmap.put("content",content);
		boardmap.put("author",author);
		boardService.insertBoard(boardmap);
		
		return "redirect:/main";
	}
	
	//게시물 출력화면
	@RequestMapping(value="/view", method=RequestMethod.GET)
	public String printboard(int board_id,Model model) throws Exception{			
		model.addAttribute("list", boardService.selectBoardTitle(board_id));
		return "view";
	}
	
	//삭제하기
	@RequestMapping(value="/delete")
	public String deleteBoard(int board_id,Model model) throws Exception{	
		 boardService.deleteBoard(board_id);
		return "redirect:/main";
	}
	
	//수정폼으로 가기
	@RequestMapping(value="/edit")
	public String updateForm(int board_id,Model model) throws Exception{	
		model.addAttribute("list", boardService.selectBoardTitle(board_id));
		return "edit";
	}
	
	//수정하기
	@RequestMapping(value="/update")
	public String updateBoard(
				@RequestParam("board_id") int board_id,
				@RequestParam("title") String title,
				@RequestParam("content") String content,
				@RequestParam("author") String author, Model model) throws Exception{

		Map<String,Object> boardmap = new HashMap<String, Object>();
		
		boardmap.put("title",title);
		boardmap.put("content",content);
		boardmap.put("author",author);
		boardmap.put("board_id",board_id);
		
		boardService.updateBoard(boardmap);
		return "redirect:/main";
	}
}
