package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class CreateFile {
    //final static Scanner sc=new Scanner(System.in);
    final static List<Task> task = new ArrayList<>();
    public enum Status{
        todo,in_progress,done

    }


    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        createFile();
        String action;
        int id;
        do {
            System.out.println();
            System.out.println("#command: add,delete,update,exit");

            System.out.print("task-cli: ");
            action = scanner.nextLine();
           // sc.nextLine();
            switch (action) {
                case "add":
                    addTask();
                    break;
                case "delete":
                    List<Task> list = fetchTask(); // Make sure this method exists
                    for (Task task : list) {
                        // System.out.println("id: " + task.getId() + ", Desc: " + task.getDescription());
                    }
                    System.out.print("task-cli: delete task id: ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("Invalid input. Please enter a number.");
                        System.out.print("task-cli: delete task id: ");
                        scanner.next(); // Consume the invalid input
                    }
                    id = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline left by nextInt()
                    deleteTask(list, id);
                    break;
                default:
                    System.out.println("error: case-invalid command. please try again.");
            }
        }
        while (!action.equals("exit"));
       // scanner.close();

    }

  /*  public static void handleAction(String action) {
        int id;
        switch (action){
            case "lockin":
                System.out.println("lock in");
                break;
            case "add":
                addTask();
                break;
            case "delete":
                List<Task> list = fetchTask();
                for (Task task : list) {
                    System.out.println("id: " + task.getId() + ", Desc: " + task.getDescription());
                }

                if (sc.hasNextInt()) {
                    System.out.print("delete id: ");
                    id = sc.nextInt();
                    deleteTask(list, id);
                }
                break;
            case "update":

                List<Task> list2=fetchTask();
                System.out.print("Update id:");
                id= sc.nextInt();
                updateTask(list2,id);

                break;
            case "list":
                if (!sc.hasNext()) {
                    System.out.println("# Listing all tasks");
                    List<Task> tasks = fetchTask();
                    if (tasks.isEmpty()) {
                        System.out.println("No tasks found.");
                        break;
                    }
                    for (Task task : tasks) {
                        System.out.println("id: " + task.getId() + ", Desc: " + task.getDescription() + ", Status: " + task.getStatus());
                    }
                } else {
                    String statusFilter = sc.next();
                    if (statusFilter.equalsIgnoreCase("todo") || statusFilter.equalsIgnoreCase("done") || statusFilter.equalsIgnoreCase("all") || statusFilter.equalsIgnoreCase("in-progress")) {
                        List<Task> tasks = fetchTask();
                        if (tasks.isEmpty()) {
                            System.out.println("No tasks found.");
                            break;
                        }

                        if (statusFilter.equalsIgnoreCase("all")) {
                            System.out.println("# Listing all tasks");
                            for (Task task : tasks) {
                                System.out.println("id: " + task.getId() + ", Desc: " + task.getDescription() + ", Status: " + task.getStatus());
                            }

                        } else if (statusFilter.equalsIgnoreCase("todo")) {
                            System.out.println("# Listing tasks by status");
                            System.out.println("# Listing tasks by status todo");
                            for (Task task : tasks) {
                                if (task.getStatus() == Status.todo) {
                                    System.out.println("id: " + task.getId() + ", Desc: " + task.getDescription() + ", Status: " + task.getStatus());
                                }
                            }
                        } else if(statusFilter.equalsIgnoreCase("done")) {
                            System.out.println("# Listing tasks by status");
                            System.out.println("# Listing tasks by status done");
                            for (Task task : tasks) {
                                if (task.getStatus() == Status.done) {
                                    System.out.println("id: " + task.getId() + ", Desc: " + task.getDescription() + ", Status: " + task.getStatus());
                                }
                            }
                        } else if(statusFilter.equalsIgnoreCase("in-progress")) {
                            System.out.println("# Listing tasks by status");
                            System.out.println("# Listing tasks by status in-progress");
                            for (Task task : tasks) {
                                if (task.getStatus() == Status.in_progress) {
                                    System.out.println("id: " + task.getId() + ", Desc: " + task.getDescription() + ", Status: " + task.getStatus());
                                }
                            }
                        }

                    } else {
                        System.out.println("Invalid filter. Use 'todo', 'done', 'in-progress', or 'all'.");
                    }
                }
                break;
            case "exit":
                exitSys();
                break;

            default:
                System.out.println("error: invalid command. please try again.");
        }
    }
*/
  public static void addTask() {
      List<Task> tt = new ArrayList<>();
      Gson gson = new Gson();
      Scanner scanner = new Scanner(System.in);
      Date date = new Date();

      File file = new File("filename.txt");
      if (file.length() != 0) {
          try (FileReader reader = new FileReader(file)) {
              Type listType = new TypeToken<List<Task>>() {}.getType();
              tt = gson.fromJson(reader, listType);
              System.out.println(tt.size());
              for (Task existtask : tt) {
                  System.out.println("id: " + existtask.getId() + ", Desc: " + existtask.getDescription());
              }
          } catch (Exception e) {
              System.out.println("Error reading tasks: " + e.getMessage());
          }
      } else {
          System.out.println("No tasks yet.");
      }

      System.out.println("Enter the number of tasks:");
      int number_of_tasks = scanner.nextInt();
      scanner.nextLine(); // Consume newline

      Status status= Status.todo;

      for (int i = 0; i < number_of_tasks; i++) {
          System.out.println("Enter description for task " + (i + 1) + ":");
          String desc = scanner.nextLine();
          tt.add(new Task(tt.size() + 1, desc, status, date.toString(), date.toString()));
      }

      try (FileWriter writer = new FileWriter("filename.txt")) {
          gson.toJson(tt, writer);
          System.out.println("Successfully wrote to the file.");
      } catch (IOException e) {
          System.out.println("Exception while writing: " + e.getMessage());
      }

      // Print the updated list to verify
      for (Task task : tt) {
          System.out.println("id: " + task.getId() + ", Desc: " + task.getDescription());
      }
  }
    public static List<Task> fetchTask(){
        List<Task> taskList=new ArrayList<>();
        Gson gson = new Gson();
        File file = new File("filename.txt");
        if(file.length()!=0) {
            try (FileReader reader = new FileReader(file)) {
                Type listType = new TypeToken<List<Task>>() {
                }.getType();
                taskList = gson.fromJson(reader, listType);
                //System.out.println(taskList.size());
            }
            catch (Exception e){
                System.out.println("error reading the file"+e);
            }

        }else {

            System.out.println("output: no data, add task first.");
        }
        return taskList;
    }
    public static  void updateTask(List<Task> t,int id){
        Gson gson = new Gson();
        Status st=Status.done;
        for (Task taskupdate: t
             ) {
            if(id==taskupdate.getId()){
                taskupdate.setStatus(st);
            }

            System.out.println(taskupdate);
        }

    }
    public static void createFile(){

        try {

            File myObj = new File("D:\\FUTURE\\TaskManager\\filename.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    private static void deleteTask(@org.jetbrains.annotations.NotNull List<Task> newTask, int id) {

        List<Task> tt = new ArrayList<>();
        Gson gson = new Gson();

        // Read the existing tasks from the file
        try (FileReader reader = new FileReader("filename.txt")) {
            Type listType = new TypeToken<List<Task>>() {}.getType();
            tt = gson.fromJson(reader, listType);
        } catch (Exception e) {
            System.out.println("Error reading tasks: " + e.getMessage());
        }

        // Check if the task with the specified id exists
        boolean taskExists = tt.removeIf(task -> task.getId() == id);

        if (taskExists) {
            System.out.println("message: successfully deleted the task (id) " + id+".");

            // Write the updated list back to the file
            try (FileWriter writer = new FileWriter("filename.txt")) {
                if(tt.isEmpty()){
                    writer.write("");
                    System.out.println("message: no tasks remain.");
                }else {
                    gson.toJson(tt, writer);
                   // System.out.println("message: successfully updated the file.");
                }
            } catch (IOException e) {
                System.out.println("Exception while writing: " + e.getMessage());
            }


            // Print the updated list to verify
            for (Task task : tt) {
               // System.out.println("task: task: (id) " + task.getId() + ", desc: " + task.getDescription());
            }
        } else {
            System.out.println("No task found with ID " + id + ". Please enter a valid ID.");
        }
    }


    private static void deleteTasks(@org.jetbrains.annotations.NotNull List<Task> newTask, int id) {

        if(newTask.size()!=0){
            List<Task> tt = new ArrayList<>();
            Gson gson = new Gson();

            // Read the existing tasks from the file
            try (FileReader reader = new FileReader("filename.txt")) {
                Type listType = new TypeToken<List<Task>>() {}.getType();
                tt = gson.fromJson(reader, listType);
            } catch (Exception e) {
                System.out.println("Error reading tasks: " + e.getMessage());
            }

            // Remove the task with the specified id
            tt.removeIf(task -> task.getId() == id);
            System.out.println("message: successfully deleted the task (id)" +id);

            // Write the updated list back to the file
            try (FileWriter writer = new FileWriter("filename.txt")) {
                gson.toJson(tt, writer);
                System.out.println("message: successfully updated the file.");
            } catch (IOException e) {
                System.out.println("Exception while writing: " + e.getMessage());
            }

            // Print the updated list to verify
            for (Task task : tt) {
                System.out.println("output : (id) " + task.getId() + ", desc: " + task.getDescription());
            }
        }

    }
    public static void addTask(Scanner scanner) {
        List<Task> tt = new ArrayList<>();
        Gson gson = new Gson();
        //Scanner scanner = new Scanner(System.in);
        Date date = new Date();

        File file = new File("filename.txt");
        if (file.length() != 0) {
            try (FileReader reader = new FileReader(file)) {
                Type listType = new TypeToken<List<Task>>() {}.getType();
                tt = gson.fromJson(reader, listType);
                int maxId = 0;

                if (!tt.isEmpty()) {
                    maxId = tt.get(0).getId();
                    for (Task eTask : tt) {
                        if (eTask.getId() > maxId) {
                            maxId = eTask.getId();
                        }
                    }
                }

                System.out.print("task-cli: how many tasks? ");
                int number_of_add_tasks = scanner.nextInt();
                scanner.nextLine();

                for (int i = 0; i < number_of_add_tasks; i++) {
                    System.out.print("task-cli: task description (id) " + (maxId + i + 1) + ": ");
                    String desc = scanner.nextLine();
                    tt.add(new Task((maxId + i + 1), desc, Status.todo, date.toString(), date.toString()));
                }

                try (FileWriter writer = new FileWriter("filename.txt")) {
                    gson.toJson(tt, writer);
                    System.out.println("message: task added successfully.");
                } catch (IOException e) {
                    System.out.println("Exception while writing: " + e.getMessage());
                }

            } catch (Exception e) {
                System.out.println("Error reading tasks: " + e.getMessage());
            }
        } else {
            System.out.print("task-cli: how many tasks? ");
            int number_of_tasks = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            for (int i = 0; i < number_of_tasks; i++) {
                System.out.print("task-cli: task description (id) " + (i + 1) + ":");
                String desc = scanner.nextLine(); // Use nextLine() here as well

                tt.add(new Task(i + 1, desc, Status.todo, date.toString(), date.toString())); // Use tt here
            }

            try (FileWriter myWriter = new FileWriter("filename.txt")) {
                gson.toJson(tt, myWriter); // Use Gson to write the list
                System.out.println("message: task added successfully.");
            } catch (Exception e) {
                System.out.println("Exception: " + e);
            }
        }
        scanner.close(); // Close the scanner to prevent resource leaks
    }

    private  static  void exitSys(){
        System.exit(1);
    }




}