import java.util.ArrayList;

class Node{

  private boolean         end;

  private String          id;

  private String          description;

  private int             length;

  private ArrayList<Node> child_nodes  = new ArrayList<>();

  public Node(boolean end, String id, int length, String description){
    
    this.end         = end;

    this.id          = id;

    this.length      = length;

    this.description = description;

  }


  public boolean is_end(){
    return this.end;
  }

  
  public String get_id(){
    return this.id;
  }

  public int length(){
    return this.length;
  }

  public String get_description(){
    return this.description;
  }

  public void set_child(Node child){
    this.child_nodes.add(child);
  }

}
