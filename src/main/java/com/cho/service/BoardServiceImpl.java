package com.cho.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cho.dao.BoardDAO;
import com.cho.domain.BoardVO;
import com.cho.domain.Criteria;
@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardDAO boardDao;

	@Override
	public List<BoardVO> selectBoard(Criteria cri) throws Exception {		
		return boardDao.selectBoard(cri);
	}
	
	@Override
	public int listCount() throws Exception {		
		return boardDao.listCount();
	}

	@Override
	public void insertBoard(Map<String, String> boardmap) throws Exception {
		boardDao.insertBoard(boardmap);
		
	}

	@Override
	public BoardVO selectBoardTitle(int board_id) throws Exception {
		BoardVO list = boardDao.selectBoardTitle(board_id);
		return list;
	}

	@Override
	public void deleteBoard(int board_id) throws Exception {
		boardDao.deleteBoard(board_id);
	}

	@Override
	public void updateBoard(Map<String, Object> boardmap) throws Exception {
		boardDao.updateBoard(boardmap);
		
	}

	
}
