package org.flimwip.design.utility;

import org.flimwip.design.Models.PopulationFile;

import javax.xml.stream.*;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class XML_Vendor_Parser {

    static final String ELEMENT = "element";
    static final String NAME = "name",ABSOLUTE = "absolute";
    static final String FROM = "from", TO = "to";

    private PopulationFile pf = null;

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

    public void write_config(String configFile, List<PopulationFile> files){
        try{
            XMLEventWriter eventWriter = XMLOutputFactory.newInstance().createXMLEventWriter(new FileOutputStream(configFile));
            XMLEventFactory eventFactory = XMLEventFactory.newFactory();
            eventWriter.add(eventFactory.createStartDocument("utf-16"));
            eventWriter.add(eventFactory.createStartElement("", "", "config"));
            for(PopulationFile pf : files){

                eventWriter.add(eventFactory.createStartElement("", "", "element"));
                eventWriter.add(eventFactory.createAttribute("name" , pf.get_name()));
                eventWriter.add(eventFactory.createStartElement("", "", "from"));
                eventWriter.add(eventFactory.createAttribute("absolute", pf.get_from_absolut() ? "true" : "false"));
                eventWriter.add(eventFactory.createCharacters(pf.get_from()));
                eventWriter.add(eventFactory.createEndElement("", "", "from"));
                eventWriter.add(eventFactory.createStartElement("", "", "to"));
                eventWriter.add(eventFactory.createAttribute("absolute", pf.get_to_absolute() ? "true" : "false"));
                eventWriter.add(eventFactory.createCharacters(pf.get_to()));
                eventWriter.add(eventFactory.createEndElement("", "", "to"));
                eventWriter.add(eventFactory.createEndElement("", "", "element"));

            }
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
