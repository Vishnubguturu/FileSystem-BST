import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BST<K extends Comparable<? super K>, V> implements DefaultMap<K, V> {
	
	Node<K, V> root;
	public BST(){this.root = null;}
	public BST(Node<K, V> root){this.root = root;}
	int size;
	
	@Override
	public boolean put(K key, V value) throws IllegalArgumentException {
		if(key == null) {
			throw new IllegalArgumentException("Key must be non-null");
		}
		root = putRecursive(root, key, value);
		if(root != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean replace(K key, V newValue) throws IllegalArgumentException {
		if(key == null) {
			throw new IllegalArgumentException("Key must be non-null");
		}
		if(containsKey(key)) {
			root = replaceRecursive(root, key, newValue);
			return true;
		}
		return false;
	}

	@Override
	public boolean remove(K key) throws IllegalArgumentException {
		if(key == null) {
			throw new IllegalArgumentException("Key must be non-null");
		}
		if(containsKey(key)) {
			root = removeRecursive(root, key);
			size--;
			return true;
		}
		return false;
	}

	@Override
	public void set(K key, V value) throws IllegalArgumentException {
		if(key == null) {
			throw new IllegalArgumentException("Key must be non-null");
		}
		root = setRecursive(root, key, value);
	}

	@Override
	public V get(K key) throws IllegalArgumentException {
		if(key == null) {
			throw new IllegalArgumentException("Key must be non-null");
		}
		return getRecursive(root, key);
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public boolean containsKey(K key) throws IllegalArgumentException {
		if(key == null) {
			throw new IllegalArgumentException("Key must be non-null");
		}
		if(get(key) != null) {
			return true;
		}
		return false;
	}

	@Override
	public List<K> keys() {
		ArrayList<K> keys = new ArrayList<>();
		inOrder(root, keys);
		return keys;
	}
	
	private Node<K, V> putRecursive(Node<K, V> node, K key, V value) {
		if(containsKey(key)) {
			return null;
		}
		if(node == null) {
			size++;
			return new Node<K, V>(key, value, null, null);
		}
		if(node.getKey().compareTo(key) < 0) {
			node.right = putRecursive(node.right, key, value);
			return node;
		}
		else if(node.getKey().compareTo(key) > 0) {
			node.left = putRecursive(node.left, key, value);
			return node;
		}
		return null;
	}
	
	private V getRecursive(Node<K, V> node, K key) {
		if(node == null) {
			return null;
		}
		if(node.getKey().equals(key)) {
			return node.getValue();
		}
		else if(node.getKey().compareTo(key) < 0) {
			return getRecursive(node.right, key);
		}
		else if(node.getKey().compareTo(key) > 0) {
			return getRecursive(node.left, key);
		}
		return null;
	}
	
	private Node<K, V> replaceRecursive(Node<K, V> node, K key, V value){
		if(node == null) {
			return null;
		}
		if(node.getKey().compareTo(key) < 0) {
			node.right = replaceRecursive(node.right, key, value);
		}
		else if(node.getKey().compareTo(key) > 0) {
			node.left = replaceRecursive(node.left, key, value);
		}
		node.setValue(value);
		return node;
	}

	private Node<K, V> removeRecursive(Node<K, V> node, K key){
		if(node == null) {
			return null;
		}
		if(node.getKey().compareTo(key) < 0) {
			node.right = removeRecursive(node.right, key);
			return node;
		}
		else if(node.getKey().compareTo(key) > 0) {
			node.left = removeRecursive(node.left, key);
			return node;
		}
		else {
			if(node.right == null) {
				return node.left;
			}
			else if(node.left == null) {
				return node.right;
			}
			else {
				Node<K, V> smallest = getSmallest(node.right);
				node.key = smallest.getKey();
				node.setValue(smallest.getValue());
				node.right = removeRecursive(smallest, smallest.getKey());
			}
			return node;
		}
	}
	
	private Node<K, V> getSmallest(Node<K, V> node){
		while(node.left == null) {
			return node;
		}
		return getSmallest(node.left);
	}
	
	private Node<K, V> setRecursive(Node<K, V> node, K key, V value){
		if(node == null) {
			size++;
			return new Node<K, V>(key, value, null, null);
		}
		if(node.getKey().compareTo(key) < 0) {
			node.right = setRecursive(node.right, key, value);
			return node;
		}
		else if(node.getKey().compareTo(key) > 0) {
			node.left = setRecursive(node.left, key, value);
			return node;
		}
		else {
			node.setValue(value);
			return node;
		}
	}
	
	private void inOrder(Node<K, V> node, ArrayList<K> list) {
		if(node == null) {
			return;
		}
		inOrder(node.left, list);
		list.add(node.getKey());
		inOrder(node.right, list);
	}
	
	
	private static class Node<K extends Comparable<? super K>, V> implements DefaultMap.Entry<K, V> {
		
		K key; 
		V value;
		Node<K, V> left; 
		Node<K, V> right;
		
		public Node(K key, V value, Node<K, V> left, Node<K, V> right){
			this.key = key; 
			this.value = value;
			this.left = left;
			this.right = right;
		}
		
		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public void setValue(V value) {
			this.value = value;
		}
		
		
	}
	 
}
