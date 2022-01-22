import java.lang.Math;
import java.util.ArrayList;

public class RankCoach extends Coach {
    private ArrayList<Coach> Coaches;
    public void getCoaches() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
	      DatabaseReference reference = database.getReference("<Server link here>");
	      reference.addChildEventListener(new ChildEventListener() {
          @Override
          public void onChildAdded(DataSnapshot dataSnapshot, String previousKey) {
            Coach newCoach = dataSnapshot.getValue(Coach.class);
            Coaches.add(newCoach);
          }

          @Override
          public void onChildChanged(DataSnapshot dataSnapshot, String previousKey) {}

          @Override
          public void onChildRemoved(DataSnapshot dataSnapshot) {}

          @Override
          public void onChildMoved(DataSnapshot dataSnapshot, String previousKey) {}

          @Override
          public void onCancelled(DatabaseError databaseError) {}
        });
        reference.addChildEventListener(new ChildEventListener() {
          @Override
          public void onChildAdded(DataSnapshot dataSnapshot, String previousKey) {}
        
          @Override
          public void onChildChanged(DataSnapshot dataSnapshot, String previousKey) {
            Coach updatedCoach = dataSnapshot.getValue(Coach.class);
            for (int i = 0; i < Coaches.size(); i++) {
              if (Coaches.get(i).getName() == updatedCoach.getName()) {
                Coaches.set(i,updatedCoach);
                break;
              }
            }
          }
        
          @Override
          public void onChildRemoved(DataSnapshot dataSnapshot) {}
        
          @Override
          public void onChildMoved(DataSnapshot dataSnapshot, String previousKey) {}
        
          @Override
          public void onCancelled(DatabaseError databaseError) {}
        });
  }
    
    public int partition(Coach coaches[], int low, int high) {
    	double pivot = coaches[high].getFinalRating(); 
 	    int i = low - 1;
    	for (int j = low; j < high; j++) {
           if (coaches[j].getFinalRating() < pivot) {
            	i++;    
            	double temp = coaches[i].getFinalRating();
	    	      coaches[i].setFinalRating(coaches[j].getFinalRating());
            	coaches[j].setFinalRating(temp);
           }
    	}
    	double tmp = coaches[i+1].getFinalRating();
      coaches[i+1].setFinalRating(coaches[high].getFinalRating());
    	coaches[high].setFinalRating(tmp);;
    	return i + 1;
    }     

    public void sortCoaches(Coach coaches[], int low, int high) {
    	if (low < high) {
        	int pa = partition(coaches, low, high);
        	sortCoaches(coaches, low, pa - 1);
        	sortCoaches(coaches, pa + 1, high); 
    	}
    }  
}
