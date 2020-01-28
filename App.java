import java.util.Scanner;

class Bank implements Cloneable {
    String name;
    double x, y ;
    boolean isBranch = false ;
    Bank(String name , double x , double y , boolean isBranch) {
        this.name = name ;
        this.x = x ;
        this.y = y ;
        this.isBranch = isBranch ;
    }
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

class Neighbor implements Cloneable {
    double x1 , y1 ;
    double x2 , y2 ;
    String name;
    Neighbor(String name , double x1 , double y1 , double x2 , double y2) {
        this.name = name;
        this.x1 = x1 ;
        this.x2 = x2 ;
        this.y1 = y1 ;
        this.y2 = y2 ;
    }
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

class Node implements Cloneable {
    Node left ;
    Node right ;
    // 0 for x 1 for y
    boolean axis ;
    Bank bank ;
    BankTrieNode rootBTT ;

    // Node Constructor
    Node(Bank bank ,boolean axis) {
        left = null ;
        right = null ;
        this.axis = axis ;
        this.bank = bank ;

    }
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public void setRootBTT(BankTrieNode rootBTT) {
        this.rootBTT = rootBTT;
    }

    // Add a New Node Recursive
    public Node addNode(Node root , Bank bank , boolean axis) {
        if (root == null) {
            Node newNode= new Node(bank , axis) ;
            root = newNode;
            return root ;
        }
        if (root.bank.x == bank.x && root.bank.y == bank.y)
            return null ;
        else {

            if (!axis) {
                if (bank.x < root.bank.x) {
                    root.left = addNode(root.left , bank , !axis);
                }
                else {
                    root.right = addNode(root.right , bank , !axis);
                }
            }
            else {

                if (bank.y < root.bank.y) {
                    root.left =addNode(root.left , bank , !axis);

                }
                else {
                    root.right = addNode(root.right , bank , !axis);

                }
            }
        }
        return root ;

    }

    public Node search(Node root , double x, double y , boolean axis) {
        if (root==null)
            return null ;
        if (root.bank.x == x && root.bank.y == y)
            return root;
        if (!axis) {
            if (x < root.bank.x)
                return search(root.left , x ,y ,!axis) ;
            else
                return search(root.right , x ,y ,!axis);
        }
        else {
            if (y< root.bank.y)
                return search(root.left , x, y ,!axis) ;
            else
                return search(root.right , x ,y ,!axis) ;
        }

    }

    // Find Min Node in Axis
    public  Node minFind(Node root , boolean axis) {
        if (root != null) {
            if (root.axis = axis) {
                if (root.left == null)
                    return root;
                else
                    return minFind(root.left, axis);
            } else {
                Node l = minFind(root.left, axis);
                Node r = minFind(root.right, axis);

                if (!axis) {
                    if (l.bank.x <= r.bank.x) {
                        if (l.bank.x <= root.bank.x)
                            return l;
                        return root;
                    } else {
                        if (r.bank.x <= root.bank.x)
                            return r;
                        return root;
                    }
                } else if (axis) {
                    if (l.bank.y <= r.bank.y) {
                        if (l.bank.y <= root.bank.y)
                            return l;
                        return root;
                    } else {
                        if (r.bank.y <= root.bank.y)
                            return r;
                        return root;
                    }
                }


            }

        }
        return new Node(new Bank("1", 99999999999999999999999.9, 99999999999999999999999.9 , false), false);
    }

    // Delete Node
    public Node DeleteNode(Node root , double x ,double y , boolean axis) {
        if(root==null)
            return null ;

        if (root.bank.x == x && root.bank.y==y ) {
            if (root.right!=null) {
                Node min = minFind(root.right , root.axis) ;
                root.bank = min.bank ;
                root.right = DeleteNode(root.right , min.bank.x , min.bank.y , !axis) ;
            }
            else if (root.left!=null) {
                Node min = minFind(root.left , root.axis) ;
                root.bank = min.bank ;
                root.right = DeleteNode(root.left , min.bank.x , min.bank.y , !axis) ;
            }
            else{
                root.bank = null;
                return null;
            }
            return root ;
        }

        if (!axis) {
            if ( x < root.bank.x)
                root.left = DeleteNode(root.left , x ,y, !axis) ;
            else
                root.right= DeleteNode(root.right, x, y, !axis);
        }
        else {
            if ( y < root.bank.y)
                root.left = DeleteNode(root.left , x ,y, !axis) ;
            else
                root.right= DeleteNode(root.right, x, y, !axis);

        }
        return root ;
    }

    // Search Bank in a Rect
    public void RectSearch(Node root , double x1 , double y1 , double x2 , double y2 , boolean axis) {
        if (root==null)
            return ;

        if (x1 <=root.bank.x && y1 <= root.bank.y && x2 >= root.bank.x && y2 >=root.bank.y ) {
            System.out.println(" Bank : " + root.bank.name);
            System.out.println(" Coordinates : x= " + root.bank.x + "  | y= " + root.bank.y);
            BankTrieNode find = rootBTT.FindBank(rootBTT ,root.bank.name ) ;
            find.numbersearched++;
        }

        if (!axis) {
            if (root.left !=null && root.right!=null) {
                if (x1<= root.bank.x && x2 >=root.bank.x){
                    RectSearch(root.left ,x1 ,y1 , x2 , y2 , !axis) ;
                    RectSearch(root.right ,x1 ,y1 , x2 , y2 , !axis) ;
                }
                else if (x1>=root.bank.x ) {
                    RectSearch(root.right ,x1,y1, x2 ,y2 ,!axis);
                }
                else if(x2 <=root.bank.x) {
                    RectSearch(root.left ,x1,y1, x2 ,y2 ,!axis);
                }
            }
            else if (root.left !=null) {
                if (x1 <=root.bank.x)
                    RectSearch(root.left ,x1,y1, x2 ,y2 ,!axis);
            }
            else if (root.right !=null) {
                if(x2 >= root.bank.x )
                    RectSearch(root.right ,x1,y1, x2 ,y2 ,!axis);
            }
        }
        else if (axis) {
            if (root.left !=null && root.right!=null) {
                if (y1<= root.bank.y && y2 >=root.bank.y){
                    RectSearch(root.left ,x1 ,y1 , x2 , y2 , !axis) ;
                    RectSearch(root.right ,x1 ,y1 , x2 , y2 , !axis) ;
                }
                else if (y1>=root.bank.y ) {
                    RectSearch(root.right ,x1,y1, x2 ,y2 ,!axis);
                }
                else if(y2 <=root.bank.y) {
                    RectSearch(root.left ,x1,y1, x2 ,y2 ,!axis);
                }
            }
            else if (root.left !=null) {
                if (y1 <=root.bank.y)
                    RectSearch(root.left ,x1,y1, x2 ,y2 ,!axis);
            }
            else if (root.right !=null) {
                if(y2 >= root.bank.y )
                    RectSearch(root.right ,x1,y1, x2 ,y2 ,!axis);
            }

        }


    }

    public void RadiusSearch(Node root , double x , double y , double r , boolean axis) {
        if (root==null)
            return ;

        if ((x-root.bank.x)* (x-root.bank.x) + (root.bank.y -y) * (root.bank.y -y) <=r*r ) {
            System.out.println(" Bank : " + root.bank.name);
            System.out.println(" Coordinates : x= " + root.bank.x + "  | y= " + root.bank.y);
            BankTrieNode find = rootBTT.FindBank(rootBTT ,root.bank.name ) ;
            find.numbersearched++;
        }

        if (!axis) {
            if (root.left !=null && root.right!=null) {
                if (x-r<= root.bank.x && x+r >=root.bank.x){
                    RadiusSearch(root.left ,x,y ,r , !axis) ;
                    RadiusSearch(root.right ,x ,y ,r , !axis) ;
                }
                else if (x-r >= root.bank.x ) {
                    RadiusSearch(root.right ,x ,y ,r ,!axis);
                }
                else if(x+r <=root.bank.x) {
                    RadiusSearch(root.left ,x ,y ,r ,!axis);
                }
            }
            else if (root.left !=null) {
                if (x-r <=root.bank.x)
                    RadiusSearch(root.left ,x,y,r ,!axis);
            }
            else if (root.right !=null) {
                if(x-r >= root.bank.x )
                    RadiusSearch(root.right ,x,y,r,!axis);
            }
        }
        else if (axis) {
            if (root.left !=null && root.right!=null) {
                if (y-r<= root.bank.y && y+r >=root.bank.y){
                    RadiusSearch(root.left ,x,y ,r , !axis) ;
                    RadiusSearch(root.right ,x ,y ,r , !axis) ;
                }
                else if (y-r >= root.bank.y ) {
                    RadiusSearch(root.right ,x ,y ,r ,!axis);
                }
                else if(y+r <=root.bank.y) {
                    RadiusSearch(root.left ,x ,y ,r ,!axis);
                }
            }
            else if (root.left !=null) {
                if (y-r <=root.bank.y)
                    RadiusSearch(root.left ,x,y,r ,!axis);
            }
            else if (root.right !=null) {
                if(y-r >= root.bank.y )
                    RadiusSearch(root.right ,x,y,r,!axis);
            }

        }


    }

    public Node NearestBank(Node root , double x , double y , boolean axis , Node near) {
        if (root==null)
            return null;
        if (dist(root.bank.x , root.bank.y , x , y) <= dist(near.bank.x , near.bank.y , x ,y)) {
            near = root ;

        }
        if (!axis) {
            if (root.left !=null && root.right!=null) {
                if(x<=root.bank.x) {
                    near = NearestBank(root.left, x, y, !axis, near);
                    if (dist(near.bank.x, near.bank.y, x, y) > dist(root.bank.x, y, x, y)) {
                        near = NearestBank(root.right, x, y, !axis, near);
                    }
                }
                else {
                    near = NearestBank(root.right, x, y, !axis, near);
                    if (dist(near.bank.x, near.bank.y, x, y) > dist(root.bank.x, y, x, y)){
                        near = NearestBank(root.left, x, y, !axis, near);
                    }
                }

            }
            else if (root.right == null && root.left!=null){
                near = NearestBank(root.left, x, y, !axis, near);
            }
            else if(root.left == null && root.right!=null){
                near = NearestBank(root.right, x, y, !axis, near);
            }
        }
        else if(axis) {
            if (root.left !=null && root.right!=null) {
                if(y<=root.bank.y) {
                    near = NearestBank(root.left, x, y, !axis, near);
                    if (dist(near.bank.x, near.bank.y, x, y) > dist(x, root.bank.y, x, y)) {
                        near = NearestBank(root.right, x, y, !axis, near);
                    }
                }
                else {
                    near = NearestBank(root.right, x, y, !axis, near);
                    if (dist(near.bank.x, near.bank.y, x, y) > dist(x, root.bank.y, x, y)){
                        near = NearestBank(root.left, x, y, !axis, near);
                    }
                }

            }
            else if (root.right == null){
                near = NearestBank(root.left, x, y, !axis, near);
            }
            else if(root.left == null){
                near = NearestBank(root.right, x, y, !axis, near);
            }


        }

        return near ;
    }

    public void PrintAllBank(Node root ,  BankTrieNode find) {
        if (root==null)
            return;
        else {
            System.out.println(" Bank : " + root.bank.name);
            System.out.println(" Coordinates : x= " + root.bank.x + "  | y= " + root.bank.y);
            find.numbersearched++;

        }
        PrintAllBank(root.left , find);
        PrintAllBank(root.right  ,find);
    }

    public double dist(double x1 , double y1 , double x2 , double y2) {
        return (x2-x1) * (x2-x1) + (y2-y1) * (y2-y1);
    }
}

class NeighborTrieTNode implements Cloneable {
    Neighbor neighborhood ;
    boolean check ;
    NeighborTrieTNode [] Nodes ;
    NeighborTrieTNode() {
        Nodes = new NeighborTrieTNode[26] ;
        check = false ;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Neighbor FindNeighbor(NeighborTrieTNode firstNode , String name) {
        NeighborTrieTNode tmpNode = firstNode ;
        for (int i=0 ; i<name.length() ; i++) {
            int idx = name.charAt(i) - 'a' ;
            if (tmpNode.Nodes[idx] == null)
                return null ;
            tmpNode = tmpNode.Nodes[idx] ;
        }
        if (tmpNode.check){
            return tmpNode.neighborhood ;
        }
        else
            return null ;
    }

    public boolean AddAndCheckForNeighbor(NeighborTrieTNode firstNode,String name , Neighbor neighborhood) {
        for (int i=0 ; i<name.length() ; i++) {
            int idx = name.charAt(i) - 'a' ;
            if (firstNode.Nodes[idx] == null)
                firstNode.Nodes[idx] = new NeighborTrieTNode() ;
            firstNode = firstNode.Nodes[idx] ;
        }

        if (firstNode.check)
            return false ;
        else {
            firstNode.check = true ;
            firstNode.neighborhood = neighborhood ;
            return true ;
        }
    }
}

class BankTrieNode implements Cloneable {
    boolean check ;
    Bank bank ;
    Node branchroot ;
    BankTrieNode[] Nodes ;
    int size = 0 ;
    static BankTrieNode maxBranchNode = new BankTrieNode() ;
    static BankTrieNode mostSearchedBAank = new BankTrieNode() ;
    int numbersearched = 0 ;


    BankTrieNode() {
        Nodes = new BankTrieNode[26] ;
        check = false ;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public BankTrieNode FindBank(BankTrieNode firstNode , String name) {
        BankTrieNode tmpNode = firstNode ;
        for (int i=0 ; i<name.length() ; i++) {
            int idx = name.charAt(i) - 'a' ;
            if (tmpNode.Nodes[idx] == null)
                return null ;
            tmpNode = tmpNode.Nodes[idx] ;
        }
        if (tmpNode.check){
            return tmpNode ;
        }
        else
            return null ;
    }


    public boolean AddAndCheckForBank(BankTrieNode firstNode,String name , Bank bank) {
        for (int i=0 ; i<name.length() ; i++) {
            int idx = name.charAt(i) - 'a' ;
            if (firstNode.Nodes[idx] == null)
                firstNode.Nodes[idx] = new BankTrieNode() ;
            firstNode = firstNode.Nodes[idx] ;
        }

        if (firstNode.check)
            return false ;
        else {
            firstNode.check = true ;
            firstNode.bank = bank ;
            firstNode.size++ ;
            return true ;
        }
    }


    public BankTrieNode AddBranch(BankTrieNode firstNode ,  String bankName  , Bank bank){
        BankTrieNode OrgBankNode = firstNode.FindBank(firstNode , bankName) ;
        if (OrgBankNode == null)
            return null ;
        if (OrgBankNode.branchroot==null)
            OrgBankNode.branchroot = new Node(bank, false);
        else
            OrgBankNode.branchroot = OrgBankNode.branchroot.addNode(OrgBankNode.branchroot , bank , false) ;
        OrgBankNode.size++ ;

        return firstNode;
    }


    public void delBranch(BankTrieNode firstNode,String orgname , double x ,double y) {
        BankTrieNode OrgBankNode = firstNode.FindBank(firstNode , orgname) ;
        OrgBankNode.branchroot = OrgBankNode.branchroot.DeleteNode(OrgBankNode.branchroot , x, y, false) ;
    }


    public void maxBranch(BankTrieNode firstNode) {
        if (firstNode.check) {
            if (firstNode.size > maxBranchNode.size) {
                maxBranchNode = firstNode;
            }
        }
        else {
            for (int i = 0; i < 26; i++) {
                if (firstNode.Nodes[i] != null) {
                    maxBranch(firstNode.Nodes[i]) ;
                }
            }
        }

    }


    public void famousBank(BankTrieNode firstNode) {
        if (firstNode.check) {
            if (firstNode.numbersearched > mostSearchedBAank.size) {
                mostSearchedBAank = firstNode;
            }
        }
        else {
            for (int i = 0; i < 26; i++) {
                if (firstNode.Nodes[i] != null) {
                    famousBank(firstNode.Nodes[i]); ;
                }
            }
        }
    }







}


class Stack<Type> {
    class Node {
        private Type data;
        private Node nextNode ;
    }

    int size ;
    Node first ;
    Stack(){
        size = 0 ;
        first = null ;
    }

    boolean isEmpty() {
        return size==0 ;
    }

    int getSize () {
        return size ;
    }

    void Push(Type item) {
        Node newNode = new Node() ;
        newNode.data = item ;
        newNode.nextNode = first ;
        first = newNode ;
        size++ ;
    }

    Type pop() {
        if (!this.isEmpty()) {
            first = first.nextNode;
            size--;
            return first.data;
        }
        return null ;
    }

    Type Top() {
        if (!this.isEmpty())
            return first.data ;
        System.out.println("Stack Empty");
        return null ;
    }
}


public class App implements Cloneable {
    Node rootKD  ;
    NeighborTrieTNode rootNTT  ;
    BankTrieNode rootBTT  ;

    App(Node rootKD , NeighborTrieTNode rootNTT , BankTrieNode rootBTT) {
        this.rootKD = rootKD ;
        this.rootBTT = rootBTT ;
        this.rootNTT = rootNTT ;
    }


    @Override
    protected Object clone() throws CloneNotSupportedException {
        App cloned = (App) super.clone();
        if (cloned.rootKD!=null)
        cloned.rootKD = (Node)rootKD.clone();
        cloned.rootBTT = (BankTrieNode) rootBTT.clone();
        cloned.rootNTT = (NeighborTrieTNode) rootNTT.clone();
        return cloned ;
    }

    void AddBank() {
        Scanner in = new Scanner(System.in) ;
        System.out.println("Enter Bank Name : ");
        String BankName  = in.nextLine();
        System.out.println("Enter Bank X Coordinate : ");
        double x  = in.nextDouble();
        System.out.println("Enter Bank Y Coordinate : ");
        double y  = in.nextDouble();

        Bank bank = new Bank(BankName , x, y , false ) ;
        if (rootKD==null) {
            boolean tmp = rootBTT.AddAndCheckForBank(rootBTT , BankName , bank ) ;
            if (tmp) {
                rootKD = new Node(bank, false);
                System.out.println("Bank Added Successfully");
            }
            else
                System.out.println("There is a Bank with this Name Already");
        }
        else {
            Node tmp = rootKD.addNode(rootKD, bank, false);
            if (tmp == null)
                System.out.println("There is Another Bank in This Location Already");
            else {
                boolean tmp2 = rootBTT.AddAndCheckForBank(rootBTT , BankName , bank ) ;
                if (tmp2) {
                    System.out.println("Bank Added Successfully");
                    rootKD = tmp ;
                }
                else
                    System.out.println("There is a Bank with this Name Already");
            }
        }
    }

    void addNeighbor() {
        Scanner in = new Scanner(System.in) ;
        String name ;
        System.out.println("Enter Neighborhood Name :");
        name= in.nextLine() ;
        double x1 ,  y1 ,  x2 ,  y2;
        System.out.println("Please Enter 2 Point :  ");
        System.out.println("X1 : ");
        x1 = in.nextDouble();
        System.out.println("Y1 : ");
        y1 = in.nextDouble();
        System.out.println("X2 : ");
        x2 = in.nextDouble();
        System.out.println("Y2 : ");
        y2 = in.nextDouble();



        Neighbor newNeighbor = new Neighbor(name, x1, y1, x2, y2) ;

//        Neighbor newNeighbor = new Neighbor("kol", -20, -20, 20, 20) ;

        boolean successadd = rootNTT.AddAndCheckForNeighbor(rootNTT , newNeighbor.name , newNeighbor) ;
        if (successadd)
            System.out.println("Neighborhood Successfully Added");
        else
            System.out.println("Neighborhood Name is already available");

    }

    void addbr(){
        Scanner in = new Scanner(System.in) ;
        System.out.println("Enter HeadQuarter Bank Name");
        String name = in.nextLine() ;
        System.out.println("Enter Branch Name");
        String branchName = in.nextLine() ;
        System.out.println("Enter Bank X Coordinate : ");
        double x  = in.nextDouble();
        System.out.println("Enter Bank Y Coordinate : ");
        double y  = in.nextDouble();
        Bank bank = new Bank(branchName , x , y, true);
        BankTrieNode tmp = rootBTT.AddBranch(rootBTT , name , bank) ;

        if (tmp == null)
            System.out.println("There is No HeadQuarter Bank with This Name");
        else {
            bank.name = name ;
            Node tmp2 = rootKD.addNode(rootKD, bank, false);
            if (tmp2 == null)
                System.out.println("There is Another Bank in This Location Already");
            else {
                System.out.println("Branch Added Successfully");
                rootKD = tmp2;
            }
        }

    }

    void deletebr(){
        if(rootKD!=null) {
            Scanner in = new Scanner(System.in);
            System.out.println("Enter X Coordinate : ");
            double x = in.nextDouble();
            System.out.println("Enter X Coordinate : ");
            double y = in.nextDouble();
            Node search = rootKD.search(rootKD, x, y, false);
            if (search == null)
                System.out.println("There is No Bank with This Coordinates");
            else {

                rootBTT.delBranch(rootBTT, search.bank.name, x, y);
                rootKD = rootKD.DeleteNode(rootKD, x, y, false);

            }
        }
        else {
            System.out.println("There is No Bank");

        }
    }

    void listBinNe(){
        if (rootKD!=null) {
            rootKD.setRootBTT(rootBTT);
            Scanner in = new Scanner(System.in);
            System.out.println("Enter Neighborhood Name : ");
            String name = in.nextLine();
            Neighbor neighborhood = rootNTT.FindNeighbor(rootNTT, name);
            if (neighborhood != null)
                rootKD.RectSearch(rootKD, neighborhood.x1, neighborhood.y1, neighborhood.x2, neighborhood.y2, false);
            else
                System.out.println("There is No Neighborhood with This Name");
        }
        else {
            System.out.println("There is No Bank");
        }
    }

    void listBrofBank() {
        if (rootKD!=null) {
            rootKD.setRootBTT(rootBTT);
            Scanner in = new Scanner(System.in);
            System.out.println("Enter HeadQuarter Bank Name : ");
            String bankname = in.nextLine();
            BankTrieNode BankNode = rootBTT.FindBank(rootBTT, bankname);
            if (BankNode == null)
                System.out.println("There is No HeadQuarter Bank with This Name");
            else {
                if (BankNode.branchroot == null)
                    System.out.println("There is No Branch for This Bank");
                else {
                    BankNode.branchroot.PrintAllBank(BankNode.branchroot, BankNode);
                }
            }
        }else {
            System.out.println("There is No Bank");

        }
    }

    void nearBank() {
        if (rootKD!=null) {
            rootKD.setRootBTT(rootBTT);
            Scanner in = new Scanner(System.in);
            System.out.println("Enter Your X Coordinate : ");
            double x = in.nextDouble();
            System.out.println("Enter Your Y Coordinate : ");
            double y = in.nextDouble();
            Node tmp = rootKD.NearestBank(rootKD, x, y, false, rootKD);
            BankTrieNode find = rootBTT.FindBank(rootBTT, tmp.bank.name);
            find.numbersearched++;
            System.out.println("Bank : " + tmp.bank.name);
            System.out.println(" Coordinates : x= " + tmp.bank.x + "  | y= " + tmp.bank.y);
        }
        else {
            System.out.println("There is No Bank");

        }

    }

    void nearBankBr(){
        if (rootKD!=null) {
            rootKD.setRootBTT(rootBTT);
            Scanner in = new Scanner(System.in);
            String BankName = in.nextLine();
            System.out.println("Enter Your  X Coordinate : ");
            double x = in.nextDouble();
            System.out.println("Enter Your Y Coordinate : ");
            double y = in.nextDouble();
            BankTrieNode OrginalBankNode = rootBTT.FindBank(rootBTT, BankName);
            if (OrginalBankNode == null)
                System.out.println("There is No HeadQuarter Bank with This Name");
            else {
                Node tmp = OrginalBankNode.branchroot.NearestBank(OrginalBankNode.branchroot, x, y, false, OrginalBankNode.branchroot);
                OrginalBankNode.numbersearched++;
                System.out.println("Bank : " + tmp.bank.name);
                System.out.println(" Coordinates : x= " + tmp.bank.x + "  | y= " + tmp.bank.y);
            }
        }else {
            System.out.println("There is No Bank");
        }

    }

    void nearRadius(){
        if (rootKD!=null) {
            rootKD.setRootBTT(rootBTT);
            Scanner in = new Scanner(System.in);
            System.out.println("Enter Your X  Coordinate : ");
            double x = in.nextDouble();
            System.out.println("Enter Your Y  Coordinate : ");
            double y = in.nextDouble();
            System.out.println("Enter Your Radius : ");
            double r = in.nextDouble();
            if (rootKD != null)
                rootKD.RadiusSearch(rootKD, x, y, r, false);
        }
        else
            System.out.println("There is No Bank in the Map");
    }

    void mostBrBank(){
        rootBTT.maxBranch(rootBTT);
        System.out.println(BankTrieNode.maxBranchNode.bank.name + " " + BankTrieNode.maxBranchNode.size );
    }

    void fameBank(){
        if (rootKD!=null) {
            rootKD.setRootBTT(rootBTT);
            rootBTT.famousBank(rootBTT);
            System.out.println(BankTrieNode.mostSearchedBAank.bank.name + " " + BankTrieNode.mostSearchedBAank.numbersearched);
        }else {
            System.out.println("There is No Bank");
        }
    }

    public static void main(String[] args) {
        App app = new App(null , new NeighborTrieTNode() , new BankTrieNode()) ;
        Stack<App> appStack = new Stack<>() ;
        try {
            appStack.Push((App)app.clone());
        }catch (Exception e){e.printStackTrace();}


        Scanner in =new Scanner(System.in) ;
        while (true) {
            System.out.println("Please Enter Your Command : \n");
            System.out.println("addN (Add Neighborhood) ");
            System.out.println("addB (Add Bank) ");
            System.out.println("addBr (Add Branch) ");
            System.out.println("delBr (Delete Bank Branch) ");
            System.out.println("listB (Banks in a Neighborhood) ");
            System.out.println("listBrs (All Branches of a Bank) ");
            System.out.println("nearB (Nearest Bank from You) ");
            System.out.println("nearBr (Nearest Branch of a Bank from You) ");
            System.out.println("availB (Available Banks from You with radius) ");
            System.out.println("mostBrs (Bank with most Branches) ");
            System.out.println("undo (Get Back to k-prev Status) ");
            System.out.println("fameB (Bank you Most Saw) ");

//            System.out.println(appStack.size);
            String input = in.nextLine() ;
            try {
                if (input.equals("addN")) {
                    app.addNeighbor();
                    try {
                        appStack.Push((App)app.clone());
                    }catch (Exception e){e.printStackTrace();}
                }
                else if (input.equals("addB")){
                    app.AddBank();
                    try {
                        appStack.Push((App)app.clone());
                    }catch (Exception e){e.printStackTrace();}
                }
                else if (input.equals("addBr")){
                    app.addbr();
                    try {
                        appStack.Push((App)app.clone());
                    }catch (Exception e){e.printStackTrace();}
                }
                else if (input.equals("delBr")){
                    app.deletebr();
                    try {
                        appStack.Push((App)app.clone());
                    }catch (Exception e){e.printStackTrace();}
                }
                else if (input.equals("listB")){
                    app.listBinNe();
                    try {
                        appStack.Push((App)app.clone());
                    }catch (Exception e){e.printStackTrace();}
                }
                else if (input.equals("listBrs")){
                    app.listBrofBank();
                    try {
                        appStack.Push((App)app.clone());
                    }catch (Exception e){e.printStackTrace();}
                }
                else if (input.equals("nearB")){
                    app.nearBank();
                    try {
                        appStack.Push((App)app.clone());
                    }catch (Exception e){e.printStackTrace();}
                }
                else if (input.equals("nearBr")){
                    app.nearBankBr();
                    try {
                        appStack.Push((App)app.clone());
                    }catch (Exception e){e.printStackTrace();}
                }
                else if (input.equals("availB")){
                    app.nearRadius();
                    try {
                        appStack.Push((App)app.clone());
                    }catch (Exception e){e.printStackTrace();}
                }
                else if (input.equals("mostBrs")){
                    app.mostBrBank();
                }
                else if (input.equals("undo")){
                    Scanner inp = new Scanner(System.in);
                    System.out.println("Enter number of Steps for Undo");
                    int steps = inp.nextInt() ;
                    for (int i= 0 ; i<steps ; i++)
                        appStack.pop() ;
                    app = appStack.Top() ;

                }
                else if (input.equals("fameB")){
                    app.fameBank();
                }
                else {
                    System.out.println("Command Not Found!!");
                }
            }catch (Exception e) {
                e.printStackTrace();
                System.out.println("SomeThing bad Happened");
            }

        }
    }

}
