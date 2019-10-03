package unl.cse.sets; //DO NOT CHANGE THIS

import java.util.HashSet;
import java.util.Set;

import unl.cse.utils.Pair;

public class SetUtils {
	
	/**
	 * example of generic set usage--this method has been done for you
	 */
	public static <T> Set<T> setMinus(Set<T> a, Set<T> b) {
		Set<T> result = new HashSet<T>();
		
		result.addAll(a);
		for(T element : b) {
			result.remove(element);
		}
		return result;
	}
	
	public static <T> Set<T> union(Set<T> a, Set<T> b) {
		return null;
	}
	
	public static <T> Set<T> intersection(Set<T> a, Set<T> b) {
		return null;
	}

	/**
	 * Returns a set containing all subsets of the given set
	 */
	public static <T> Set<Set<T>> getPowerSet(Set<T> set) {
		return null;
	}

	/**
	 * Returns a set containing all subsets of the given set with the specified cardinality
	 */
	public static <T> Set<Set<T>> getSetsOfCardinality(Set<T> set, int size) {
		return null;
	}
	
	/**
	 * Returns the symmetric difference of the two given sets
	 */
	public static <T> Set<T> symmetricDifference(Set<T> a, Set<T> b) {
		return null;
	}

	public static <S, T> Set<Pair<S, T>> cartesianProduct(Set<S> a, Set<T> b) {
		return null;
	}

    public static void main(String args[]) {
	//you can use the pair class by making a pair:
	Pair<String, String> pairOfStrings = Pair.make("Hello", "World");
	
    }
	
}
