package com.ramtech.netty.poc;

import io.netty.resolver.HostsFileEntries;
import io.netty.resolver.HostsFileParser;

public class Reproducer {

    public static void main(String[] args) {
        HostsFileEntries entries = HostsFileParser.parseSilently();
        System.out.println(entries.inet4Entries());
        System.out.println(entries.inet6Entries());
    }
}
