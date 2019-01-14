package app;

import java.util.ArrayList;

public class BTree {

    Node parentNode;

    public BTree(Node parentNode){
        this.parentNode = parentNode;
    }


    public void addKey(Key newKey){

        if(this.parentNode.keys[0] == null){
            parentNode.keys[0] = newKey;
        }else if(this.parentNode.keys[0].leftNode == null){

            int insertionPlace = -1;
            Node node = this.parentNode;
            for(int i = 0; i < node.keys.length; i++){

                if(node.keys[i] == null){

                    node.keys[i] = newKey;
                    insertionPlace = i;
                }

                if(insertionPlace != -1){

                    if(insertionPlace == node.keys.length - 1){

                        int middleIndex = node.keys.length/2;
                        Key middleKey = node.keys[middleIndex];

                        Node rightNode = new Node(node.max);
                        Node leftNode = new Node(node.max);

                        int leftIndex = 0, rightIndex = 0;

                        for(int j = 0; j < node.keys.length; j++){

                            if(node.keys[j].value < middleKey.value){

                                leftNode.keys[leftIndex]  = node.keys[j];
                                leftIndex++;
                                node.keys[j] = null;

                            }else if(node.keys[j].value > middleKey.value){

                                rightNode.keys[rightIndex] = node.keys[j];
                                rightIndex++;
                                node.keys[j] = null;
                            }
                        }
                        node.keys[middleIndex] = null;

                        this.parentNode = node;
                        this.parentNode.keys[0] = middleKey;

                        leftNode.parentNode = this.parentNode;
                        rightNode.parentNode = this.parentNode;

                        middleKey.leftNode = leftNode;
                        middleKey.rightNode= rightNode;

                        break;

                    }else {
                        break;
                    }
                }
            }

        }else if(this.parentNode.keys[0].leftNode != null){

            Node node = this.parentNode;

            while(node.keys[0].leftNode != null){

                int loop = 0;
                
                for(int i = 0; i < node.keys.length; i++, loop++){
                
                    if(node.keys[i] != null) {
                        if (node.keys[i].value > newKey.value) {
                            node = node.keys[i].leftNode;
                            break;
                        }
                    }
                    else{
                        node = node.keys[i - 1].rightNode;
                        break;
                    }
                }
                
                if (loop == node.keys.length) {
                    node = node.keys[loop - 1].rightNode;
                }
            }

            int indexPlaced = placeNode(node, newKey);

            if(indexPlaced == node.keys.length - 1){

                while (node.parentNode != null) {

                    int middleIndex = node.keys.length / 2;

                    Key middleKey = node.keys[middleIndex];
                    Node leftNode = new Node(node.max);
                    Node rightNode = new Node(node.max);
                    int leftIndex = 0, rightIndex = 0;

                    for (int i = 0; i < node.keys.length; i++) {

                        if (node.keys[i].value < middleKey.value) {

                            leftNode.keys[leftIndex] = node.keys[i];
                            leftIndex++;
                            node.keys[i] = null;

                        } else if (node.keys[i].value > middleKey.value) {

                            rightNode.keys[rightIndex] = node.keys[i];
                            rightIndex++;
                            node.keys[i] = null;
                        }
                    }

                    node.keys[middleIndex] = null;
                    middleKey.leftNode = leftNode;
                    middleKey.rightNode = rightNode;

                    node = node.parentNode;

                    leftNode.parentNode = node;
                    rightNode.parentNode = node;

                    for(int k = 0; k < leftNode.keys.length; k++){

                        if(leftNode.keys[k] != null){

                            if(leftNode.keys[k].leftNode != null)
                                leftNode.keys[k].leftNode.parentNode = leftNode;
                            if(leftNode.keys[k].rightNode != null)
                                leftNode.keys[k].rightNode.parentNode = leftNode;
                        }
                    }

                    for(int k = 0; k < rightNode.keys.length; k++){

                        if(rightNode.keys[k] != null){
                            if(rightNode.keys[k].leftNode != null)
                                rightNode.keys[k].leftNode.parentNode = rightNode;
                            if(rightNode.keys[k].rightNode != null)
                                rightNode.keys[k].rightNode.parentNode = rightNode;
                        }
                    }

                    int placedIndex = placeNode(node, middleKey);
                    if(placedIndex == node.keys.length - 1){

                        if(node.parentNode == null){

                            int middleIndexRoot = node.keys.length / 2;
                            Key middleKeyRoot = node.keys[middleIndexRoot];
                            Node leftNodeRoot = new Node(node.max);
                            Node rightNodeRoot = new Node(node.max);
                            int leftIndexRoot = 0; int rightIndexRoot = 0;

                            for (int i = 0; i < node.keys.length; i++) {

                                if (node.keys[i].value < middleKeyRoot.value) {

                                    leftNodeRoot.keys[leftIndexRoot] = node.keys[i];
                                    leftIndexRoot++;
                                    node.keys[i] = null;

                                } else if (node.keys[i].value > middleKeyRoot.value) {

                                    rightNodeRoot.keys[rightIndexRoot] = node.keys[i];
                                    rightIndexRoot++;
                                    node.keys[i] = null;
                                }
                            }
                            node.keys[middleIndexRoot] = null;
                            node.keys[0] = middleKeyRoot;

                            for(int k = 0; k < leftNodeRoot.keys.length; k++){

                                if(leftNodeRoot.keys[k] != null){
                                    leftNodeRoot.keys[k].leftNode.parentNode = leftNodeRoot;
                                    leftNodeRoot.keys[k].rightNode.parentNode = leftNodeRoot;
                                }
                            }

                            for(int k = 0; k < rightNodeRoot.keys.length; k++){

                                if(rightNodeRoot.keys[k] != null){
                                    rightNodeRoot.keys[k].leftNode.parentNode = rightNodeRoot;
                                    rightNodeRoot.keys[k].rightNode.parentNode = rightNodeRoot;
                                }
                            }
                            middleKeyRoot.leftNode = leftNodeRoot;
                            middleKeyRoot.rightNode = rightNodeRoot;

                            leftNodeRoot.parentNode = node;
                            rightNodeRoot.parentNode = node;

                            this.parentNode = node;

                        }
                        continue;
                    }else {
                        break;
                    }
                }
            }
        }
    }

    public int placeNode(Node node, Key newKey){

        int indexPlaced = -1;
        for(int i = 0; i < node.keys.length; i++){

            if (node.keys[i] == null){

                boolean placed = false;
                for(int j = i - 1; j >=0 ; j--){

                    if(node.keys[j].value > newKey.value){
                        node.keys[j + 1] = node.keys[j];
                    }else{
                        node.keys[j + 1] = newKey;
                        node.keys[j].rightNode = newKey.leftNode;

                        placed = true;
                        break;
                    }
                }
                if(placed == false){
                    node.keys[0] = newKey;
                    node.keys[1].leftNode = newKey.rightNode;
                }
                indexPlaced = i;
                break;
            }
        }

        return indexPlaced;
    }

    ArrayList<Integer> xaxis = new ArrayList<>();

    public void printKeys(Key[] keys, int startX, int startY){
        for(int i = 0; i < keys.length; i++){

            if(keys[i] != null){

                if(keys[i].leftNode != null) {

                    printKeys(keys[i].leftNode.keys, startX - 15, startY + 15);
                }
            }

            if(keys[i] != null) {

                if(xaxis.contains(startX))
                    System.out.println("Parent: X = " + (startX + 15) + "    Y = " + startY);
                else
                    System.out.println("Parent: X = " + startX + "    Y = " + startY);

                xaxis.add(startX);
                System.out.println(keys[i].value);
            }

            if(keys[i] != null){

                if(keys[i].rightNode != null) {
                    if(xaxis.contains(startX))
                        printKeys(keys[i].rightNode.keys, startX + 15, startY + 15);
                    else
                        printKeys(keys[i].rightNode.keys, startX, startY + 15);
                }
            }

        }
        System.out.println();
    }

    public void deleteKey(int key) {

        Node node = parentNode;
        int index = -1;

        while (node != null) {

            boolean found = false;
            for (int i = 0; i < node.keys.length; i++) {

                if (node.keys[i] == null) {

                    node = node.keys[i - 1].rightNode;
                    break;
                } else if (node.keys[i].value > key) {
                    node = node.keys[i].leftNode;
                    break;
                } else if (node.keys[i].value == key) {
                    index = i;
                    found = true;
                    break;
                }
            }

            if (found == true) {
                break;
            }
        }

        if (index == -1) {
            System.out.println("Not Found");
        }
        else if (node.keys[index].leftNode == null && node.keys[1] != null) {
            node.keys[index] = null;

            for (int i = index; i < node.keys.length - 1; i++) {

                node.keys[i] = node.keys[i + 1];
            }
            node.keys[node.keys.length - 1] = null;

        } else if (node.keys[index].leftNode == null && node.keys[1] == null) {

            Key rightKey = null, leftKey = null; Node nodeParent = node.parentNode;

            for (int i = 0; i < nodeParent.keys.length; i++) {

                if (nodeParent.keys[i] != null) {
                    if (nodeParent.keys[i].rightNode == node) {
                        rightKey = nodeParent.keys[i];
                    } else if (nodeParent.keys[i].leftNode == node) {
                        leftKey = nodeParent.keys[i];
                    }
                }
            }

            if (leftKey != null && leftKey.rightNode.keys[1] != null) {
                node.keys[0].value = leftKey.value;
                leftKey.value = leftKey.rightNode.keys[0].value;

                for(int i = 0; i < leftKey.rightNode.keys.length - 1; i++){
                    leftKey.rightNode.keys[i] = leftKey.rightNode.keys[i + 1];
                }
                leftKey.rightNode.keys[leftKey.rightNode.keys.length - 1] = null;


            } else if (rightKey != null && rightKey.leftNode.keys[1] != null) {

                node.keys[0].value = rightKey.value;

                for(int i = 0; i < rightKey.leftNode.keys.length; i++){

                    if(rightKey.leftNode.keys[i] == null){
                        rightKey.value =  rightKey.leftNode.keys[i - 1].value;
                        rightKey.leftNode.keys[i - 1] = null;
                        break;
                    }
                }


            }else if(leftKey != null && rightKey == null && nodeParent.keys[1] != null){

                Node rightNode = leftKey.rightNode;
                leftKey.rightNode = null;
                leftKey.leftNode = null;

                for(int i = 0; i < rightNode.keys.length; i++){

                    if(rightNode.keys[i] == null){

                        for(int j = i; j > 0; j--){
                            rightNode.keys[j] = rightNode.keys[j - 1];
                        }
                        break;
                    }
                }

                rightNode.keys[0] = leftKey;
                for(int i = 0; i < nodeParent.keys.length - 1; i++){

                    nodeParent.keys[i] = nodeParent.keys[i + 1];
                }
                nodeParent.keys[nodeParent.keys.length - 1] = null;

            }else if(leftKey != null && rightKey != null){

                Node leftNode = rightKey.leftNode;

                rightKey.leftNode = null;
                rightKey.rightNode = null;

                for(int i = 0; i < leftNode.keys.length; i++){
                    if(leftNode.keys[i] == null){
                        leftNode.keys[i] = rightKey;
                        break;
                    }
                }

                leftKey.leftNode = leftNode;

                for(int i = 0; i < nodeParent.keys.length; i++){

                    if(nodeParent.keys[i] == rightKey){

                        for(int j = i; j < nodeParent.keys.length - 1; j++){

                            nodeParent.keys[j] = nodeParent.keys[j + 1];
                        }
                        nodeParent.keys[nodeParent.keys.length - 1] = null;
                        break;
                    }
                }


            }else if(rightKey != null && leftKey == null && nodeParent.keys[1] != null){

                Node leftNode = rightKey.leftNode;
                rightKey.leftNode = null;
                rightKey.rightNode = null;

                for(int i = 0; i < nodeParent.keys.length; i++){

                    if(nodeParent.keys[i].value == rightKey.value){
                        nodeParent.keys[i] = null;
                        break;
                    }
                }

                for(int i = 0; i < leftNode.keys.length; i++){

                    if(leftNode.keys[i] == null){
                        leftNode.keys[i] = rightKey;
                        break;
                    }
                }

            }else {
                System.out.println("Delete node and merge the above nodes till parent node" );

                Node newNode = null;
                while (nodeParent != null) {
                    Key keyToMerge = null;
                    int loopBreak = 2;
                    for (int i = 0; i < nodeParent.keys.length; i++) {
                            if (nodeParent.keys[i].rightNode == node) {

                                if(key == 12){
                                }
                                if (i > 0) {
                                    loopBreak = 1;
                                } else {
                                    loopBreak = 0;
                                }
                                keyToMerge = nodeParent.keys[i];
                                if(keyToMerge.leftNode.keys[1] == null){

                                    for (int y = i; y < nodeParent.keys.length - 1; y++) {
                                        nodeParent.keys[y] = nodeParent.keys[y + 1];
                                    }
                                    nodeParent.keys[nodeParent.keys.length - 1] = null;
                                    for (int j = 0; j < keyToMerge.leftNode.keys.length; j++) {

                                        if (keyToMerge.leftNode.keys[j] == null) {
                                            Node leftNode = keyToMerge.leftNode;
                                            keyToMerge.leftNode.keys[j] = keyToMerge;
                                            keyToMerge.leftNode = keyToMerge.leftNode.keys[j - 1].rightNode;
                                            keyToMerge.rightNode = newNode;

                                            if(newNode != null){
                                                newNode.parentNode = leftNode;
                                            }

                                            newNode = leftNode;
                                            if (nodeParent.parentNode == null && nodeParent.keys[0] == null) {
                                                this.parentNode = newNode;
                                            }
                                            break;
                                        }
                                    }

                                    if(nodeParent.keys[i] != null){

                                        nodeParent.keys[i].leftNode = newNode;
                                        if(i - 1 > 0){

                                            nodeParent.keys[i - 1].rightNode = newNode;
                                        }
                                        loopBreak = 1;
                                    }

                                }else if(keyToMerge.leftNode.keys[1] != null){

                                    Key rightMostKey = null;
                                    for(int m = 0; m < keyToMerge.leftNode.keys.length; m++){

                                        if(keyToMerge.leftNode.keys[m] == null){
                                            rightMostKey = keyToMerge.leftNode.keys[m - 1];
                                            keyToMerge.leftNode.keys[m - 1] = null;
                                            break;
                                        }
                                    }
                                    rightMostKey.leftNode = keyToMerge.leftNode;
                                    keyToMerge.leftNode = rightMostKey.rightNode;
                                    keyToMerge.leftNode.parentNode = node;
                                    rightMostKey.rightNode = node;
                                    node.keys[0] = keyToMerge;
                                    keyToMerge.rightNode = newNode;
                                    newNode.parentNode = node;
                                    nodeParent.keys[i] = rightMostKey;
                                    loopBreak = 1;

                                }
                            } else if(nodeParent.keys[i].leftNode == node) {

                                if (i > 0) {
                                    loopBreak = 1;
                                } else {
                                    loopBreak = 0;
                                }
                                keyToMerge = nodeParent.keys[i];

                                if(keyToMerge.rightNode.keys[1] == null){
                                for (int y = i; y < nodeParent.keys.length - 1; y++) {
                                    nodeParent.keys[y] = nodeParent.keys[y + 1];
                                }
                                nodeParent.keys[nodeParent.keys.length - 1] = null;
                                for (int j = 0; j < keyToMerge.rightNode.keys.length; j++) {

                                    if (keyToMerge.rightNode.keys[j] == null) {

                                        Node rightNode = keyToMerge.rightNode;
                                        for (int k = j; k > 0; k--) {
                                            rightNode.keys[k] = rightNode.keys[k - 1];
                                        }
                                        rightNode.keys[0] = keyToMerge;

                                        keyToMerge.rightNode = keyToMerge.rightNode.keys[1].leftNode;
                                        keyToMerge.leftNode = newNode;

                                        if(newNode != null){

                                            newNode.parentNode = rightNode;
                                        }

                                        newNode = rightNode;
                                        if (nodeParent.parentNode == null && nodeParent.keys[0] == null) {
                                            this.parentNode = newNode;
                                        }
                                        break;
                                    }
                                }

                                    if(nodeParent.keys[i] != null){

                                        nodeParent.keys[i].leftNode = newNode;
                                        if(i - 1 > 0){

                                            nodeParent.keys[i - 1].rightNode = newNode;
                                        }
                                        loopBreak = 1;
                                    }

                            }else if(keyToMerge.rightNode.keys[1] != null){

                                    System.out.println("Attention here....");
                                }

                            }

                        if (loopBreak == 0) {
                            node = nodeParent;
                            nodeParent = nodeParent.parentNode;
                            newNode.parentNode = nodeParent;
                            break;
                        }else if(loopBreak == 1) {
                            nodeParent = null;
                            break;
                        }
                    }
                }
            }

        }
        else if(node.keys[index].leftNode != null && node.keys[index].rightNode != null){
            System.out.println("Not a leaf node........");
            if(node.keys[index].leftNode.keys[1] != null){
                System.out.println("Left side contains more then one node.");

            }else if(node.keys[index].rightNode.keys[1] != null){
                System.out.println("Right side contains more then one node.");
            }
        }
    }

    public Key[] getParentKey(){

        return parentNode.keys;
    }
}

