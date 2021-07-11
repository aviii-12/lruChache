package sample;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class makeLRU<T> {
    private final int capacity;
    private int size;
    private Map<String, Node> mp;
    private DoublyList deque;
    public makeLRU(final int capacity) {
        this.capacity = capacity;
        this.mp = new HashMap<>();
        this.deque = new DoublyList();
        this.size = 0;
    }

    public void put(final String key, T val) {
        Node current = mp.get(key);
        if(current != null) {
            current.val = val;
            deque.moveNodeToFront(current);
        } else {
            if(size == capacity) {
                String endNodeKey = deque.getEndKey();
                deque.removeNodeFromEnd();
                mp.remove(endNodeKey);
                size--;
            }
            Node node = new Node(key, val);
            mp.put(key, node);
            deque.addNodeToFront(node);
            size++;
        }
    }

    public T get(final String key) {
        Node node = mp.get(key);
        if(node == null)
            return null;
        deque.moveNodeToFront(node);
        return node.val;
    }

    private class Node{
        String key;
        T val;
        Node prev, next;
        public Node(String key, T val){
            this.key = key;
            this.val = val;
            this.prev = null;
            this.next = null;
        }
    }

    private class DoublyList {
        private Node front, end;
        public DoublyList(){
            front = null;
            end = null;
        }

        public void moveNodeToFront(final Node node) {
            if(front == node)
                return;
            if(node == end) {
                end = node.prev;
                if(end != null)
                    end.next = null;
            } else {
                node.prev.next = node.next;
                node.next.prev = node.prev;
            }
            this.addNodeToFront(node);
        }

        public void addNodeToFront(Node node) {
            if(end == null) {
                front = end = node;
            } else{
                node.next = front;
                front.prev = node;
                front = node;
                front.prev = null;
            }
        }

        public void removeNodeFromEnd() {
            if(front == end) {
                front = end = null;
            } else {
                end = end.prev;
                end.next = null;
            }
        }

        public String getEndKey(){
            return end.key;
        }
    }

}


public class lruCache{
    public static void main(String[] args) throws IOException {
        makeLRU<Integer> lru = new makeLRU<>(5);
        lru.put("a", 1);
        lru.put("b",2);
        lru.put("c", 3);
        lru.put("d", 4);
        lru.put("e", 5);
        Integer res = lru.get("d");
        Integer res2 = lru.get("g");
        System.out.println(res);
        if(res2 == null) {
            System.out.println("Not Found");
            lru.put("f", 6);
            res2 = lru.get("f");
            System.out.println(res2);
        }
        res = lru.get("a");
        if(res == null)
            System.out.println("Not found");
    }
}

