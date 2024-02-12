package zadanieJava.com.zad10;


import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


@Route("")
public class MainView extends VerticalLayout {
     List<String> lista = new ArrayList<>();

    public static boolean protectSave = true;
    public MainView()  {
        add(new H1("Lista zakupów"));
        var addButton = new Button("Dodaj zakupy");
        var showLast = new Button("Pokaż zapisaną liste zakupów");
        var saveButton = new Button("Zapisz listę zakupów");
        String fileName= "shopList";
        boolean showLastBool=true;
        Button [] deleteButton = new Button[100];


        TextField placeWriteText = new TextField();

        TextField [] displayText= new TextField[100];
        AtomicInteger Count= new AtomicInteger(0);
        Paragraph [] paragraphs = new Paragraph[100];
        //Button showProducts= new Button("Pokaż wszystkie produkty");


        add(new HorizontalLayout(placeWriteText,addButton, showLast,saveButton));


        showLast.addClickListener(ex -> {

            //System.out.println("lista proba");
            for(int i=0;i< Count.intValue();i++)
            {
                paragraphs[i].setVisible(false);
                deleteButton[i].setVisible(false);
            }
            Count.set(0);
            lista.clear();

            readFile(fileName,addButton,placeWriteText);

        });

        saveButton.addClickListener(e -> {
            File myfile = new File(fileName);
            myfile.delete();
            for(int i=0;i<lista.size();i++) {
                //System.out.println("lista proba");
                if(!lista.isEmpty())
                {
                    if(lista.get(i)!=null)
                    writeFIle("ShopList", lista.get(i));
                }


            }





        });
        placeWriteText.addKeyPressListener(Key.ENTER,e->{addButton.click();});

        addButton.addClickListener(e->{



            //if(protectSave==false) {saveButton.click();}
            lista.add(placeWriteText.getValue());



            deleteButton[Count.intValue()]= new Button("Usuń");
            displayText[Count.intValue()] = new TextField();
            displayText[Count.intValue()].setValue(placeWriteText.getValue());
            paragraphs[Count.intValue()]= new Paragraph(placeWriteText.getValue());
            add(paragraphs[Count.intValue()]);
            add(deleteButton[Count.intValue()]);
            placeWriteText.clear();
            int wartosc=1;

            Count.getAndIncrement();


            for(int i=0;i< Count.intValue();i++)
            {

                int finalI = i;

                deleteButton[i].addClickListener(ex->{

                                System.out.println(finalI);
                    System.out.println(lista.get(finalI));
                                lista.set(finalI,null);
                            paragraphs[finalI].setVisible(false);
                    deleteButton[finalI].setEnabled(false);
                    deleteButton[finalI].setVisible(false);

                        }
                );
            }



        });


    }
    public String readFile(String fileName,Button addButton,TextField placeTextWrite)
    {
        Path path= Paths.get(fileName);

        if(Files.exists(path)) {
            try {
                File myfile = new File(fileName);
                Scanner myScanner = new Scanner(myfile);
                while (myScanner.hasNextLine())
                {
                    placeTextWrite.setValue(myScanner.nextLine());
                    addButton.click();
                }
                myScanner.close();

            } catch (FileNotFoundException e) {
                System.out.println("wystapil blad");
                e.printStackTrace();
            }
        }
        else
            writeFIle(fileName,"");
        return "";
    }
    public void writeFIle (String fileName, String line)
    {
        try{
            FileWriter fw;
            fw = new FileWriter(fileName,true);
            fw.write(line+"\n");
            fw.close();
        }
        catch (IOException e)
        {
            System.out.println("wystapil blad");
            e.printStackTrace();
        }
    }



}