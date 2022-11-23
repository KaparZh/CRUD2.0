package com.kaparzh.crud2.view;

import com.kaparzh.crud2.controller.PostController;
import com.kaparzh.crud2.controller.WriterController;
import com.kaparzh.crud2.model.Post;
import com.kaparzh.crud2.model.Writer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WriterView {

    public static final String WRITER_MENU = "Select action:\n" +
            "1.Add writer\n" +
            "2.Update writer\n" +
            "3.Delete writer\n" +
            "4.Get writer by Id\n" +
            "5.Get list of all writers\n" +
            "0.Back to main menu\n";

    private final PostController postController = new PostController();
    private final WriterController writerController = new WriterController();

    public void writerViewRunner(int value) {
        switch (value) {
            case 0:
                break;
            case 1:
                addWriter();
                break;
            case 2:
                updateWriter();
                break;
            case 3:
                deleteWriter();
                break;
            case 4:
                getWriterById();
                break;
            case 5:
                getAll();
                break;
        }
    }

    private void addWriter() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter firstName:");
        String firstName = sc.nextLine();
        System.out.println("Enter lastName:");
        String lastName = sc.nextLine();

        List<Post> postList = getPosts();

        Writer writer = writerController.addWriter(firstName, lastName, postList);
        System.out.println("Created writer: " + writer);
    }

    private void updateWriter() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter writer id");
        int id = Integer.parseInt(sc.nextLine());

        List<Writer> writerList = writerController.getAll();
        for (Writer w : writerList) {
            if (w.getId() == id) {
                System.out.println("Enter new firstName:");
                String firstName = sc.nextLine();
                System.out.println("Enter new lastName:");
                String lastName = sc.nextLine();

                Writer writer = writerController.updateWriter(id, firstName, lastName);
                System.out.println("Updated writer: " + writer);
            }
        }
    }

    private List<Post> getPosts() {
        List<Post> allPosts = postController.getAll();
        List<Post> writerPosts = new ArrayList<>();
        System.out.println("Choose post id (enter: 0, if you done):");
        allPosts.forEach(System.out::println);

        Scanner sc = new Scanner(System.in);

        int postId = sc.nextInt();
        while (postId != 0) {
            for (Post post : allPosts) {
                if (post.getId() == postId) {
                    writerPosts.add(post);
                }
            }
            postId = sc.nextInt();
        }
        return writerPosts;
    }

    private void deleteWriter() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter writer id for delete:");
        int id = Integer.parseInt(sc.nextLine());
        writerController.deleteById(id);
    }

    private void getWriterById() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter writer id:");
        int id = Integer.parseInt(sc.nextLine());
        Writer writer = writerController.getById(id);
        System.out.println("Writer: " + writer);
    }

    private void getAll() {
        List<Writer> writers = writerController.getAll();
        writers.forEach(System.out::println);
    }
}
