package org.zerock.b01.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {

    @Builder.Default
    private int page=1;

    @Builder.Default
    private int size=10;

    private String type; //검색 t,c,w,tc,tw,tcw
    private String keyword; //검색 단어 java

    public String[] getTypes(){  //tcw  -> t c w ==> 문자열을 개별 문다로 분해해서 스트링 배열 저장
        if(type == null || type.isEmpty()){
            return null;
        }
        return null;
    }

    // abc, def ; abc ; aaa, bbb, ccc
    public Pageable getPageable(String...props){ //가변인자..
        return (Pageable) PageRequest.of(this.page -1, this.size, Sort.by(props).descending()); //page -1해야 page 실제 등록된 넘버가 나옴
    }

    private String link;

    public String getLink(){  // 검색조건과 페이징 조건을 문자열로 구성

        if(link == null){
            StringBuilder builder = new StringBuilder();
            builder.append("page="+this.page); // page=0
            builder.append("&size="+this.size); //page=0&size=10

            if(type != null && type.length() > 0){
                builder.append("&type="+type);
            }

            if(keyword != null){
                try{
                    builder.append("&keyword="+ URLEncoder.encode(keyword, "UTF-8"));
                }catch (UnsupportedEncodingException e){
                }
            }
            link = builder.toString();


        }
        return link;
    }

}
