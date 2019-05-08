package com.eight.demo.chap8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class HandleBufferReader {

    /**
     * 处理文件
     * @return
     */
    public static String processFile(BufferedReaderProcessor p) throws IOException {
        try(BufferedReader br = new BufferedReader(new FileReader("src/com/eight/demo/chap8/data.txt"))) {
            return p.process(br);
        }
    }

    public interface BufferedReaderProcessor{
        String process(BufferedReader b) throws IOException;
    }

    public static void main(String[] args) throws IOException {
        System.out.println(processFile((BufferedReader b) -> b.readLine()));

        System.out.println(processFile((BufferedReader b) -> b.readLine() + b.readLine()));
    }
}
