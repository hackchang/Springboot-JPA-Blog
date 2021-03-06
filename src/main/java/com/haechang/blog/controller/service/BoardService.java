package com.haechang.blog.controller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haechang.blog.controller.dto.LikeSaveRequestDto;
import com.haechang.blog.controller.dto.ReplySaveRequestDto;
import com.haechang.blog.model.Board;
import com.haechang.blog.model.User;
import com.haechang.blog.repository.BoardLikeRepository;
import com.haechang.blog.repository.BoardRepository;
import com.haechang.blog.repository.ReplyRepository;
import com.haechang.blog.repository.UserRepository;

//스프링이 컴포넌트 스캔을 통해 bean에 등록해줌 (IoC)
@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ReplyRepository replyRepository;

	@Autowired
	private BoardLikeRepository boardLikeRepository;

	@Transactional
	public void write(Board board, User user) { // title, content
		board.setViewcount(0);
		board.setLikecount(0);
		board.setUser(user);
		boardRepository.save(board);
	}

	@Transactional(readOnly = true)
	public Page<Board> boardList(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public Board detail(int id) {
		return boardRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
		});
	}

	@Transactional
	public void delete(int id) {
		boardRepository.deleteById(id);
	}

	@Transactional
	public void update(int id, Board requestBoard) {
		Board board = boardRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.");
		}); // 영속화
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		// 해당 함수 종료시에 (Service가 종료될 때) 트랜잭션이 종료된다. 이 때 더티체킹이 일어난다. ( 자동업데이트 (db -
		// flush))
	}

	@Transactional
	public void reply(ReplySaveRequestDto replySaveRequestDto) {
		replyRepository.mSave(replySaveRequestDto.getUserId(), replySaveRequestDto.getBoardId(),
				replySaveRequestDto.getContent());
	}

	@Transactional
	public void replyDelete(int replyId) {
		replyRepository.deleteById(replyId);
	}

	@Transactional
	public void boardLikeSave(LikeSaveRequestDto likeSaveRequestDto) {
		boardLikeRepository.mLikeSave(likeSaveRequestDto.getBoardId(), likeSaveRequestDto.getUserId());
	}

	@Transactional
	public void boardLikeDelete(LikeSaveRequestDto likeSaveRequestDto) {
		boardLikeRepository.mLikeDelete(likeSaveRequestDto.getBoardId(), likeSaveRequestDto.getUserId());
	}
}
