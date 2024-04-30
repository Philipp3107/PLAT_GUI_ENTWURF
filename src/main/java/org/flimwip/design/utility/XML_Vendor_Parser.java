package org.flimwip.design.utility;

import org.flimwip.design.Models.PopulationFile;
import org.flimwip.design.Documentationhandler.*;

import javax.xml.stream.*;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



@ServiceC(desc="Class that reads and writes XML-Files for the Vendor to save Jobs",
related={"Vendor", "Job"})
public class XML_Vendor_Parser {
    
    @ServiceATT(desc="The categorical String called \"element\" that limits the an element in the XML-File.",
                type="String",
                related={"None"})
    static final String ELEMENT = "element";
    
    @ServiceATT(desc="The categorical String called \"name\" that limits the name of the file that needs to be pushed in the XML-File.",
                type="String",
                related={"None"})
    static final String NAME = "name";
    
    @ServiceATT(desc="The categorical String called \"absolute\" that adds the attribute if the Filepathis absolute or not in the XML-File.",
                type="String",
                related={"None"})
    static final String ABSOLUTE = "absolute";
    
    @ServiceATT(desc="The categorical String called \"from\" that adds the path of the file which needs to be pushed in the XML-File.",
                type="String",
                related={"None"})
    static final String FROM = "from";
    
    @ServiceATT(desc="The categorical String called \"to\" that adds the the path of the file or directory of where the files need to be pushed to in the XML-File.",
                type="String",
                related={"None"})
    static final String TO = "to";

    @ServiceATT(desc="The generated PopulationFile",
                type="PopulationFile",
                related={"PopulationFile"})
    private PopulationFile pf = null;

    @ServiceM(desc="<##>Generates a List of PopulationFiles read from the XML-File.",
              category="Method",
              params={"configFile: String -> The Path of the XML-File where the "},
              returns="List<PopulationFile> -> Files read from the XML-Files",
              thrown={"XMLStreamException -> if the XML-File has an unaccepted form", "FileNotFoundException -> If the Files that should be parsed can not be found"},
              related={"PopulationFile"})
    public List<PopulationFile> read_confif(String configFile)
    {
        List<PopulationFile> files = new ArrayList<>();
        try{
            XMLEventReader eventReader = XMLInputFactory.newInstance().createXMLEventReader(new FileInputStream(configFile));
            while(eventReader.hasNext()){
                XMLEvent event = eventReader.nextEvent();
                if(event.isStartElement()){
                    switch (event.asStartElement().getName().getLocalPart()){
                        case ELEMENT -> new_population_file(event);
                        case FROM -> new_from(eventReader, event);
                        case TO -> new_to(eventReader, event);
                    }
                }else if(event.isEndElement() && event.asEndElement().getName().getLocalPart().equals(ELEMENT)){
                    files.add(this.pf);
                }
            }

        } catch (XMLStreamException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return files;
    }

    @ServiceM(desc="<##>Writes a new XML-File given by the ",
              category="Method",
              params={"configFile: String -> Path of the File where the data should be written", "files: List<PopulationFile> -> The Files that need to be written to the XML-File"},
              returns="void",
              thrown={"XMLStreamException -> if the XML-File has an unaccepted form", "FileNotFoundException -> If the Files that should be parsed can not be found"},
              related={"PopulationFile"})
    public void write_config(String configFile, List<PopulationFile> files){
        try{
            XMLEventWriter eventWriter = XMLOutputFactory.newInstance().createXMLEventWriter(new FileOutputStream(configFile));
            XMLEventFactory eventFactory = XMLEventFactory.newFactory();
            eventWriter.add(eventFactory.createStartDocument("utf-16"));
            eventWriter.add(eventFactory.createSpace("\n"));
            eventWriter.add(eventFactory.createStartElement("", "", "config"));
            for(PopulationFile pf : files){
                eventWriter.add(eventFactory.createSpace("\n    "));
                eventWriter.add(eventFactory.createStartElement("", "", "element"));
                eventWriter.add(eventFactory.createAttribute("name" , pf.get_name()));
                eventWriter.add(eventFactory.createSpace("\n        "));
                eventWriter.add(eventFactory.createStartElement("", "", "from"));
                eventWriter.add(eventFactory.createAttribute("absolute", pf.get_from_absolut() ? "true" : "false"));
                eventWriter.add(eventFactory.createCharacters(pf.get_from()));
                eventWriter.add(eventFactory.createEndElement("", "", "from"));
                eventWriter.add(eventFactory.createSpace("\n        "));
                eventWriter.add(eventFactory.createStartElement("", "", "to"));
                eventWriter.add(eventFactory.createAttribute("absolute", pf.get_to_absolute() ? "true" : "false"));
                eventWriter.add(eventFactory.createCharacters(pf.get_to()));
                eventWriter.add(eventFactory.createEndElement("", "", "to"));
                eventWriter.add(eventFactory.createSpace("\n    "));
                eventWriter.add(eventFactory.createEndElement("", "", "element"));

            }
            eventWriter.add(eventFactory.createSpace("\n"));
            eventWriter.add(eventFactory.createEndElement("", "", "config"));
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    public void new_population_file(XMLEvent event)
    {
        this.pf = new PopulationFile();
        Iterator<Attribute> attributes = event.asStartElement().getAttributes();
        while(attributes.hasNext()){
            Attribute attribute = attributes.next();
            if (attribute.getName().toString().equals(NAME)) {
                pf.set_name(attribute.getValue());
            }
        }
    }

    public void new_from(XMLEventReader reader, XMLEvent event) throws XMLStreamException
    {
        this.pf.set_from(reader.nextEvent().asCharacters().getData());
        Iterator<Attribute> attributes = event.asStartElement().getAttributes();
        while(attributes.hasNext()){
            Attribute attribute = attributes.next();
            if(attribute.getName().toString().equals(ABSOLUTE)){
                pf.set_from_absolute(attribute.getValue().equals("true"));
            }
        }
    }

    public void new_to(XMLEventReader reader, XMLEvent event) throws XMLStreamException
    {
        this.pf.set_to(reader.nextEvent().asCharacters().getData());
        Iterator<Attribute> attributes = event.asStartElement().getAttributes();
        while(attributes.hasNext()){
            Attribute attribute = attributes.next();
            if(attribute.getName().toString().equals(ABSOLUTE)){
                pf.set_to_absolute(attribute.getValue().equals("true"));
            }
        }
    }



}
