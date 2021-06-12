package com.unist;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class AppRemoved {
    @Test
    void callingTheThing() throws IOException {
        App.main(new String[] {});
    }
}