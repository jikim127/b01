package org.zerock.b01.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.zerock.b01.domain.Board;
import org.zerock.b01.dto.BoardDTO;
import org.zerock.b01.dto.BoardListReplyCountDTO;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.repository.BoardRepository;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService{

    private final ModelMapper modelMapper;  //DTO(BoardDTO)를 Entity(Board)로 변환하기 위함
    private final BoardRepository boardRepository;  //영속계층에 저장하기 위해서.. (DB로 저장전에 cash에 저장해놈?)

    @Override
    public Long register(BoardDTO boardDTO) {
        Board board = modelMapper.map(boardDTO, Board.class);
        Long bno = boardRepository.save(board).getBno();
        // -------------- DB저장 -------------------> 저장된 bno값을 반환

        return bno;
    }
    @Override
    public BoardDTO readOne(Long bno){
        Optional<Board> result = boardRepository.findById(bno);
        Board board = result.orElseThrow();

        BoardDTO boardDTO = modelMapper.map(board, BoardDTO.class);

        return boardDTO;
    }

    @Override
    public void modify(BoardDTO boardDTO){
        Optional<Board> result = boardRepository.findById(boardDTO.getBno());

        Board board = result.orElseThrow();
        board.change(boardDTO.getTitle(), boardDTO.getContent());
        boardRepository.save(board);   //save --> insert, update 두 기능을 수행
    }

    @Override
    public void remove(Long bno){
        boardRepository.deleteById(bno);
    }

    @Override
    public PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO){
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("bno");

        //BoardSerachImpl에서 던진 정보를 result가 다 받아줌.
        Page<Board> result = boardRepository.searchAll(types,keyword,pageable);

        log.info("--------------------------------------------------------------------");
        log.info("aaa getTotalPages: "+result.getTotalPages());
        log.info("aaa getSize: "+result.getSize());
        log.info("aaa getTotalElements: "+result.getTotalElements());
        result.getContent().forEach(board -> log.info(board));
        log.info("aaa getTotalPages: "+result.getTotalPages());
        log.info("--------------------------------------------------------------------");


        //board(entity) ---> boardDTO로 변환(mapper)
        List<BoardDTO> dtoList = result.getContent().stream()
                .map(board -> modelMapper.map(board, BoardDTO.class)).collect(Collectors.toList());

        PageResponseDTO<BoardDTO> pageResponseDTO = new PageResponseDTO<>(pageRequestDTO, dtoList, (int)result.getTotalElements());

        return pageResponseDTO;

//        return PageResponseDTO.<BoardDTO>withAll()
//                .pageRequestDTO(pageRequestDTO)
//                .dtoList(dtoList)
//                .total((int)result.getTotalElements())
//                .build();
    }

    @Override
    public PageResponseDTO<BoardListReplyCountDTO> listWithReplyCount(PageRequestDTO pageRequestDTO) {
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("bno");


        Page<BoardListReplyCountDTO> result = boardRepository.searchWithReplyCount(types, keyword, pageable);

        return PageResponseDTO.<BoardListReplyCountDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(result.getContent())
                .total((int)result.getTotalElements())
                .build();
    }
}
