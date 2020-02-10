package manners;

import java.io.*;
import java.util.*;

public class DataReader {
    FileReader input;

    public DataReader(String fileName) throws FileNotFoundException {
        input = new FileReader(fileName);
    }

    public Collection read() throws IOException {
        LinkedHashMap set = new LinkedHashMap();
        BufferedReader in = new BufferedReader(input);
        String line = in.readLine();
        while(line != null) {
            if(!line.startsWith("#")) {
                StringTokenizer tokenizer = new StringTokenizer(line);
                String name  = tokenizer.nextToken();
                String sex   = tokenizer.nextToken();
                String hobby = tokenizer.nextToken();
                Guest guest = (Guest) set.get(name);
                if(guest == null) {
                    set.put(name, new Guest(name, sex, hobby));
                }
                else {
                    guest.addHobby(hobby);
                }
            }
            line = in.readLine();
        }
        in.close();
        return set.values();
    }

    public void printData(Collection set) {
        Iterator ite = set.iterator();
        while(ite.hasNext()) {
            Guest guest = (Guest) ite.next();
            System.out.println(guest);
        }
    }

    static public class Guest {

        String name;
        String sex;
        ArrayList hobbies;

        public Guest(String name, String sex, String hobby) {
            this.name = name;
            this.sex = sex;
            hobbies = new ArrayList();
            hobbies.add(hobby);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public void addHobby(String hobby) {
            hobbies.add(hobby);
        }

        public String[] getHobbies() {
            String[] arr = new String[hobbies.size()];
            for(int i = 0; i < hobbies.size(); i++) {
                arr[i] = (String) hobbies.get(i);
            }
            return arr;
        }

        public String toString() {
            String listHobbies="";
            for(int i = 0; i < hobbies.size(); i++) {
                if(i != 0)
                    listHobbies += ", ";
                listHobbies += hobbies.get(i);
            }
            return name + "(" + sex + "[" + listHobbies + "])";
        }
    }
}
