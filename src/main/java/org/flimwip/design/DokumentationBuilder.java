package org.flimwip.design;

import org.flimwip.design.Documentationhandler.ServiceC;
import org.flimwip.design.Documentationhandler.ServiceATT;
import org.flimwip.design.Documentationhandler.ServiceCR;
import org.flimwip.design.Documentationhandler.ServiceM;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.AccessFlag;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class DokumentationBuilder {

    ArrayList<String> realted = new ArrayList<>();
    private String document = "";

    private String class_name;
    Class c;
    public DokumentationBuilder(Class c){



        this.c = c;

        class_name = this.c.getName().split("\\.")[this.c.getName().split("\\.").length-1];

        System.out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        document += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        System.out.println("<!DOCTYPE topic\n" +
                "        SYSTEM \"https://resources.jetbrains.com/writerside/1.0/xhtml-entities.dtd\">");
        document += "<!DOCTYPE topic\n" +
                "        SYSTEM \"https://resources.jetbrains.com/writerside/1.0/xhtml-entities.dtd\">\n";
        System.out.println(STR."<topic xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\nxsi:noNamespaceSchemaLocation=\"https://resources.jetbrains.com/writerside/1.0/topic.v2.xsd\"\n title=\"\{class_name}\" id=\"\{class_name}\">");
        document += STR."<topic xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\nxsi:noNamespaceSchemaLocation=\"https://resources.jetbrains.com/writerside/1.0/topic.v2.xsd\"\n title=\"\{class_name}\" id=\"\{class_name}\"> \n";
        build_doc();
        document += "</topic>";
        System.out.println("</topic>");
        System.out.println(document);
    }

    public void build_doc() {
        ServiceC class_defeinition = (ServiceC) c.getAnnotation(ServiceC.class);
        System.out.println(STR."<card-summary> \{class_defeinition.desc()} </card-summary>");
        System.out.println("<chapter title=\"Description\" id=\"description\">");
        System.out.println(STR."<p>\{class_defeinition.desc()} </p>");
        System.out.println("</chapter>");

        document += STR."<card-summary> \{class_defeinition.desc()} </card-summary>\n<chapter title=\"Description\" id=\"description\">\n<p>\{class_defeinition.desc()} </p>\n<p>This description was automatically gernerated by DokumentationBuilder.</p>\n</chapter>\n";

        if(!class_defeinition.related()[0].equals("None")){
            System.out.println("<p> <u><b>Related:</b></u></p>");
            document += "<p> <u><b>Related:</b></u></p>\n";
            for(String s : class_defeinition.related()){
                System.out.println("<a href=\""+s +".topic\"/>");
                document += "<a href=\""+s +".topic\"/>\n";
            }
        }

        for(String s: class_defeinition.related()){
            if(!realted.contains(s) && !s.equals("None")){
                realted.add(s);
            }
        }

        build_attributes();
        build_constructor();
        build_methods();
        build_getter();
        build_setter();
        build_see_also();
        document += "</topic>";
        write_into_file();

    }

    public void build_attributes(){

        document += "<chapter title=\"Attributes\" id=\"attributes\">\n<available-only-for> since v.1.2</available-only-for>\n<deflist type=\"full\">\n";
        System.out.println("<chapter title=\"Attributes\" id=\"attributes\">");
        System.out.println("<available-only-for> since v.1.2</available-only-for>");
        System.out.println("<deflist type=\"full\">");

        for(Field field : c.getDeclaredFields()){
            ServiceATT att = field.getAnnotation(ServiceATT.class);
            if(att != null){
                System.out.println(STR."<def title=\"\{field.getName()}: \{att.type()}\">");
                document += STR."<def title=\"\{field.getName()}: \{att.type()}\">\n";
                System.out.println(STR."<p>\{att.desc()}</p>");
                document +=  STR."<p>\{att.desc()}</p>\n";
                if(!att.related()[0].equals("None")){
                    System.out.println("<p> <u><b>Related:</b></u></p>");
                    document += "<p> <u><b>Related:</b></u></p>\n";
                    for(String s : att.related()){
                        System.out.println("<a href=\""+s +".topic\"/>");
                        document += "<a href=\""+s +".topic\"/>\n";
                    }
                }
                System.out.println("</def>");
                document += "</def>\n";

                for(String s : att.related()){
                    if(!this.realted.contains(s) && !s.equals("None")){
                        this.realted.add(s);
                    }
                }
            }


        }
        document += "</deflist>\n</chapter>\n";
        System.out.println("</deflist>");
        System.out.println("</chapter>");
    }

    public void build_constructor(){
        System.out.println("<chapter title=\"Constructor\" id=\"constructor\">");
        System.out.println("<available-only-for> since v.1.2</available-only-for>");
        System.out.println("<deflist type=\"full\">");

        document += "<chapter title=\"Constructor\" id=\"constructor\">" + "\n" + "<available-only-for> since v.1.2</available-only-for>" + "\n" + "<deflist type=\"full\">\n";

        for(Constructor c : c.getConstructors()){
            ServiceCR cr = (ServiceCR) c.getAnnotation(ServiceCR.class);
            if(cr != null){
                System.out.println(STR."<def title=\"\{c.getName().split("\\.")[c.getName().split("\\.").length-1]}\">");
                document += STR."<def title=\"\{c.getName().split("\\.")[c.getName().split("\\.").length-1]}\">\n";
                System.out.println(STR."<p>\{cr.desc()}</p>");
                document += STR."<p>\{cr.desc()}</p>";
                System.out.println("</def>");
                document += "</def>\n";
                System.out.println(STR."<def title=\"Parameters:\">");
                document += STR."<def title=\"Parameters:\">\n";
                for(String s : cr.params()){
                    System.out.println(STR."<p> - \{s} </p>");
                    document += STR."<p> - \{s} </p>\n";
                }
                System.out.println("</def>");
                document += "</def>\n";

                if(!cr.related()[0].equals("None")){
                    System.out.println(STR."<def title=\"Related:\">");
                    document += STR."<def title=\"Related:\">\n";
                    for(String s : cr.related()){
                        if(!this.realted.contains(s)){
                            this.realted.add(s);
                        }
                        System.out.println(STR."<p><a hef=\{s}.topic/></p>");
                    }
                    System.out.println("</def>");
                }
            }
        }
        document += "</deflist>\n</chapter>\n";
        System.out.println("</deflist>");
        System.out.println("</chapter>");
    }
    public void build_methods(){
        System.out.println("<chapter title=\"Methods\" id=\"methods\">");
        System.out.println("<available-only-for> since v.1.2</available-only-for>");
        document += "<chapter title=\"Methods\" id=\"methods\">" + "\n" + "<available-only-for> since v.1.2</available-only-for>" + "\n";
        for(Method m : c.getDeclaredMethods()){
            ServiceM mm = m.getAnnotation(ServiceM.class);
            if(mm != null){
                if(mm.category().equals("Method")){
                    System.out.println(STR."<chapter title=\"\{m.getName()}\">");
                    document += STR."<chapter title=\"\{m.getName()}\">\n";
                    System.out.println(STR."<p>\{mm.desc()}</p>");
                    document +=  STR."<p>\{mm.desc()}</p>\n";
                    System.out.println("<deflist type=\"full\">");
                    document +=  STR."<deflist type=\"full\">\n";
                    System.out.println(STR."<def title=\"Parameters:\">");
                    document += STR."<def title=\"Parameters:\">\n";
                    for(String s: mm.params()){
                        System.out.println(STR."<p> - \{s} </p>");
                        document += STR."<p> - \{s} </p>" +"\n";
                    }
                    System.out.println("</def>");
                    document += "</def>\n";

                    System.out.println(STR."<def title=\"Thrown:\">");
                    document += STR."<def title=\"Thrown:\">" + "\n";
                    for(String s: mm.thrown()){
                        System.out.println(STR."<p> - \{s} </p>");
                        document += STR."<p> - \{s} </p>\n";
                    }
                    System.out.println("</def>");
                    document += "</def>\n";
                    System.out.println(STR."<def title=\"Return:\">");
                    document += STR."<def title=\"Return:\">\n";
                    System.out.println(STR."<p> - \{mm.returns()} </p>");
                    document += STR."<p> - \{mm.returns()} </p>\n";
                    System.out.println("</def>");
                    document += "</def>\n";


                    System.out.println(STR."<def title=\"Access:\">");
                    document += STR."<def title=\"Access:\">\n";
                    for(AccessFlag s: m.accessFlags()){
                        System.out.println(STR."<p> - \{s.toString()} </p>");
                        document += STR."<p> - \{s.toString()} </p>\n";
                    }
                    System.out.println("</def>");
                    document += "</def>\n";
                    System.out.println(STR."<def title=\"Related:\">");
                    document += STR."<def title=\"Related:\">\n";
                    if(!mm.related()[0].equals("None")){
                        for(String s: mm.related()){
                            if(!this.realted.contains(s)){
                                this.realted.add(s);
                            }
                            System.out.println(STR."<p> - \{s} </p>");
                            document += STR."<p> - \{s} </p>\n";
                        }
                    }else{
                        for(String s: mm.related()){
                            System.out.println(STR."<p> - \{s} </p>");
                            document += STR."<p> - \{s} </p>\n";
                        }
                    }

                    System.out.println("</def>");
                    document += "</def>\n";
                    document += "</deflist>\n</chapter>\n";
                    System.out.println("</deflist>");
                    System.out.println("</chapter>");
                }
            }

        }

        System.out.println("</chapter>");
        document += "</chapter>\n";
    }

    public void build_getter(){
        System.out.println("<chapter title=\"Getter\" id=\"getter\">");
        System.out.println("<available-only-for> since v.1.2</available-only-for>");
        document += "<chapter title=\"Getter\" id=\"getter\">" + "\n" + "<available-only-for> since v.1.2</available-only-for>" + "\n";
        for(Method m : c.getDeclaredMethods()){
            ServiceM mm = m.getAnnotation(ServiceM.class);
            if(mm != null){
                if(mm.category().equals("Getter")){
                    System.out.println(STR."<chapter title=\"\{m.getName()}\">");
                    document += STR."<chapter title=\"\{m.getName()}\">\n";
                    System.out.println(STR."<p>\{mm.desc()}</p>");
                    document +=  STR."<p>\{mm.desc()}</p>\n";
                    System.out.println("<deflist type=\"full\">");
                    document +=  STR."<deflist type=\"full\">\n";

                    if(!mm.params()[0].equals("None")){
                        System.out.println(STR."<def title=\"Parameters:\">");
                        document += STR."<def title=\"Parameters:\">\n";
                        for(String s: mm.params()){
                            System.out.println(STR."<p> - \{s} </p>");
                            document += STR."<p> - \{s} </p>" +"\n";
                        }
                        System.out.println("</def>");
                        document += "</def>\n";
                    }


                    if(!mm.thrown()[0].equals("None")){
                        System.out.println(STR."<def title=\"Thrown:\">");
                        document += STR."<def title=\"Thrown:\">" + "\n";
                        for(String s: mm.thrown()){
                            System.out.println(STR."<p> - \{s} </p>");
                            document += STR."<p> - \{s} </p>\n";
                        }
                        System.out.println("</def>");
                        document += "</def>\n";
                    }
                    System.out.println(STR."<def title=\"Return:\">");
                    document += STR."<def title=\"Return:\">\n";
                    System.out.println(STR."<p> - \{mm.returns()} </p>");
                    document += STR."<p> - \{mm.returns()} </p>\n";
                    System.out.println("</def>");
                    document += "</def>\n";


                    System.out.println(STR."<def title=\"Access:\">");
                    document += STR."<def title=\"Access:\">\n";
                    for(AccessFlag s: m.accessFlags()){
                        System.out.println(STR."<p> - \{s.toString()} </p>");
                        document += STR."<p> - \{s.toString()} </p>\n";
                    }
                    System.out.println("</def>");
                    document += "</def>\n";
                    System.out.println(STR."<def title=\"Related:\">");
                    document += STR."<def title=\"Related:\">\n";
                    if(!mm.related()[0].equals("None")){
                        for(String s: mm.related()){
                            if(!this.realted.contains(s)){
                                this.realted.add(s);
                            }
                            System.out.println(STR."<p> - \{s} </p>");
                            document += STR."<p> - \{s} </p>\n";
                        }
                    }else{
                        for(String s: mm.related()){
                            System.out.println(STR."<p> - \{s} </p>");
                            document += STR."<p> - \{s} </p>\n";
                        }
                    }

                    System.out.println("</def>");
                    document += "</def>\n";
                    document += "</deflist>\n</chapter>\n";
                    System.out.println("</deflist>");
                    System.out.println("</chapter>");
                }
            }

        }

        System.out.println("</chapter>");
        document += "</chapter>\n";
    }

    public void build_setter(){
        System.out.println("<chapter title=\"Setter\" id=\"setter\">");
        System.out.println("<available-only-for> since v.1.2</available-only-for>");

        document += "<chapter title=\"Setter\" id=\"setter\">" + "\n" + "<available-only-for> since v.1.2</available-only-for>" + "\n";
        for(Method m : c.getDeclaredMethods()){
            ServiceM mm = m.getAnnotation(ServiceM.class);
            if(mm != null){
                if(mm.category().equals("Setter")){

                    System.out.println(STR."<chapter title=\"\{m.getName()}\">");
                    document += STR."<chapter title=\"\{m.getName()}\">\n";
                    System.out.println(STR."<p>\{mm.desc()}</p>");
                    document +=  STR."<p>\{mm.desc()}</p>\n";
                    System.out.println("<deflist type=\"full\">");
                    document +=  STR."<deflist type=\"full\">\n";
                    System.out.println(STR."<def title=\"Parameters:\">");
                    document += STR."<def title=\"Parameters:\">\n";
                    for(String s: mm.params()){
                        System.out.println(STR."<p> - \{s} </p>");
                        document += STR."<p> - \{s} </p>" +"\n";
                    }
                    System.out.println("</def>");
                    document += "</def>\n";

                    System.out.println(STR."<def title=\"Thrown:\">");
                    document += STR."<def title=\"Thrown:\">" + "\n";
                    for(String s: mm.thrown()){
                        System.out.println(STR."<p> - \{s} </p>");
                        document += STR."<p> - \{s} </p>\n";
                    }
                    System.out.println("</def>");
                    document += "</def>\n";
                    System.out.println(STR."<def title=\"Return:\">");
                    document += STR."<def title=\"Return:\">\n";
                    System.out.println(STR."<p> - \{mm.returns()} </p>");
                    document += STR."<p> - \{mm.returns()} </p>\n";
                    System.out.println("</def>");
                    document += "</def>\n";


                    System.out.println(STR."<def title=\"Access:\">");
                    document += STR."<def title=\"Access:\">\n";
                    for(AccessFlag s: m.accessFlags()){
                        System.out.println(STR."<p> - \{s.toString()} </p>");
                        document += STR."<p> - \{s.toString()} </p>\n";
                    }
                    System.out.println("</def>");
                    document += "</def>\n";
                    System.out.println(STR."<def title=\"Related:\">");
                    document += STR."<def title=\"Related:\">\n";
                    if(!mm.related()[0].equals("None")){
                        for(String s: mm.related()){
                            if(!this.realted.contains(s)){
                                this.realted.add(s);
                            }
                            System.out.println(STR."<p> - \{s} </p>");
                            document += STR."<p> - \{s} </p>\n";
                        }
                    }else{
                        for(String s: mm.related()){
                            System.out.println(STR."<p> - \{s} </p>");
                            document += STR."<p> - \{s} </p>\n";
                        }
                    }

                    System.out.println("</def>");
                    document += "</def>\n";
                    document += "</deflist>\n</chapter>\n";
                    System.out.println("</deflist>");
                    System.out.println("</chapter>");
                }

            }

        }

        System.out.println("</chapter>");
        document += "</chapter>\n";
    }

    public void build_see_also(){
        System.out.println("<seealso>");
        document += "<seealso>\n";
        System.out.println("<category ref=\"related\">");
        document += "<category ref=\"related\">\n";
        for(String s: this.realted){
            System.out.println(STR."<a href=\"\{s}.topic\"/>");
            document += STR."<a href=\"\{s}.topic\"/>\n";
        }
        System.out.println("</category>");
        document += "</category>\n";
        System.out.println("</seealso>");
        document += "</seealso>\n";

    }

    public void write_into_file(){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(STR."C:\\Users\\KotteP\\IdeaProjects\\design\\plat\\topics\\\{class_name}.topic"))){
            bw.write(document);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}


