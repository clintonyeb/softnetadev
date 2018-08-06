package com.clintonyeb.SoftnetaDev.helpers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Reader;

import static org.assertj.core.api.Java6Assertions.assertThat;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
public class UtilityTest {

    /**
     * Test when wrong url is given
     */
    @Test
    public void makeHTTRequestTest_1() {
        // given wrong url
        String url = "abc";

        // when
        Reader reader = Utility.makeHTTPRequest(url);

        // then
        assertThat(reader).isEqualTo(null);
    }

    /**
     * Test when right url is given
     */
    @Test
    public void makeHTTRequestTest_2() {
        // given wrong url
        String url = "https://www.15min.lt/rss";

        // when
        Reader reader = Utility.makeHTTPRequest(url);

        // then
        assertThat(reader).isNotEqualTo(null);
    }

    /**
     * Test when there is a connection problem
     * NB: Turn internet off to test this
     */
//    @Test
//    public void makeHTTRequestTest_3(){
//        // given wrong url
//        String url = "https://www.15min.lt/rss";
//
//        // when
//        Reader reader = Utility.makeHTTPRequest(url);
//
//        // then
//        assertThat(reader).isEqualTo(null);
//    }
}
