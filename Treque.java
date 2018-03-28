import java.util.AbstractList;
import java.util.List;

public class Treque<T> extends AbstractList<T> {

		private OpenArrayDeque<T> front, back;
   	private Class<T> type;


	public Treque(Class<T> t) {
		type = t;
		front = new OpenArrayDeque<T>(t);
		back = new OpenArrayDeque<T>(t);
	}

	public T get(int i) {
		if (i < 0 || i > size() - 1) throw new IndexOutOfBoundsException();
		if(i < front.size()){
      return front.get(i);
    }else{
      return back.get(i-front.size());
    }
	}

	public T set(int i, T x) {
		if (i < 0 || i > size() - 1) throw new IndexOutOfBoundsException();
		if(i < front.size()){
      return front.set(i, x);
    }else{
      return back.set(i-front.size(), x);
    }
	}

	public void add(int i, T x) {
		if (i < 0 || i > size()) throw new IndexOutOfBoundsException();
		if(i < front.size()){
      front.add(i, x);
    }else{
      back.add(i-front.size(), x);
    }

		if(front.size() == back.size() + 2){
			back.add(0, front.remove(front.size()-1));
		}else if(back.size() == front.size() + 2){
			front.add(front.size(), back.remove(0));
		}
	}

	public T remove(int i) {
		if (i < 0 || i > size() - 1) throw new IndexOutOfBoundsException();
		T x;
    if(i < front.size()){
      x = front.remove(i);
    }else{
      x = back.remove(i-front.size());
    }

		if(front.size() == back.size() + 2){
			back.add(0, front.remove(front.size()-1));
		}else if(back.size() == front.size() + 2){
			front.add(front.size(), back.remove(0));
		}

    return x;
	}

	public int size() {
		return front.size() + back.size();
	}

}
