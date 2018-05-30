package com.practice.springdata.reactice.redis.modle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Luo Bao Ding
 * @since 2018/5/29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coffee {
    private String id;

    private String name;
}
