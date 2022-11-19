package com.kaparzh.crud2.view;

import com.kaparzh.crud2.repository.impl.JdbcConnection;

import java.util.Scanner;

import static com.kaparzh.crud2.view.LabelView.LABEL_MENU;
import static com.kaparzh.crud2.view.PostView.POST_MENU;
import static com.kaparzh.crud2.view.WriterView.WRITER_MENU;

public class ConsoleView {

    private static final String WELCOME_MESSAGE = "Welcome!\n" +
            "Select sections:\n" +
            "1.Writer\n" +
            "2.Post\n" +
            "3.Label\n" +
            "4.Exit\n";

    private final WriterView writerView = new WriterView();
    private final LabelView labelView = new LabelView();
    private final PostView postView = new PostView();

    public void run() {
        while (true) {
            System.out.println(WELCOME_MESSAGE);
            Scanner sc = new Scanner(System.in);
            int select = sc.nextInt();
            switch (select) {
                case 1:
                    System.out.println(WRITER_MENU);
                    int writerMenuSelectValue = sc.nextInt();
                    writerView.writerViewRunner(writerMenuSelectValue);
                    break;

                case 2:
                    System.out.println(POST_MENU);
                    int postMenuSelectValue = sc.nextInt();
                    postView.postViewRunner(postMenuSelectValue);
                    break;

                case 3:
                    System.out.println(LABEL_MENU);
                    int labelMenuSelectValue = sc.nextInt();
                    labelView.labelViewRunner(labelMenuSelectValue);
                    break;

                case 4:
                    JdbcConnection.closeConnection();
                    return;
            }
        }
    }
}
