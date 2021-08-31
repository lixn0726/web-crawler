package com.lixnstudy.webcrawler.myCode.entity;


import lombok.Getter;
import lombok.Setter;

/**
 * @author lixn
 * @ClassName Movie
 * @Description TODO
 * @create 2021/8/27 5:01 下午
 **/
@Getter
@Setter
public class Movie {
    private String id;
    private String directors;
    private String title;
    private String cover;
    private String rate;
    private String casts;
}
