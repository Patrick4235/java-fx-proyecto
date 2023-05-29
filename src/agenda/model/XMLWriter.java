package agenda.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import agenda.control.Person;
import agenda.util.DateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Serializer;

public class XMLWriter{

    //ESTA CLASE FUNCIONA POR SI SOLA PERO ESTA EN DESUSO
    public static void main(String[] args) throws UnsupportedEncodingException,
            IOException{
        Element root = new Element("persons");

        ObservableList<Person> personData = FXCollections.observableArrayList();
        personData.add(new Person("Hans", "Muster"));
        personData.add(new Person("Ruth", "Mueller"));

        // add all the people
        for (Person person : personData){

            // make the main person element <person>
            Element personElement = new Element("person");

            // make the name element and it's children: first and last
            Element birthdayElement = new Element("birthday");
            Element cityElement = new Element("city");
            Element firstNameElement = new Element("firstName");
            Element lastNameElement = new Element("lastName");
            Element postalCodeElement = new Element("postalCode");
            Element streetElement = new Element("street");

            // add value to names
            birthdayElement.appendChild(DateUtil.format(person.getBirthday()));
            cityElement.appendChild(person.getCity());
            firstNameElement.appendChild(person.getFirstName());
            lastNameElement.appendChild(person.getLastName());
            postalCodeElement.appendChild(Integer.toString(person.getPostalCode()));
            streetElement.appendChild(person.getStreet());

            // add all contents to person
            personElement.appendChild(birthdayElement);
            personElement.appendChild(cityElement);
            personElement.appendChild(firstNameElement);
            personElement.appendChild(lastNameElement);
            personElement.appendChild(postalCodeElement);
            personElement.appendChild(streetElement);

            // add person to root
            root.appendChild(personElement);
        }

        // create doc off of root
        Document doc = new Document(root);

        // the file it will be stored in
        File file = new File("out.xml");
        if (!file.exists()){
            file.createNewFile();
        }

        // get a file output stream ready
        FileOutputStream fileOutputStream = new FileOutputStream(file);

        // use the serializer class to write it all
        Serializer serializer = new Serializer(fileOutputStream, "UTF-8");
        serializer.setIndent(4);
        serializer.write(doc);
    }
}