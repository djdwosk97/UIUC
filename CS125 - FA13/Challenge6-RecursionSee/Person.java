//UIUC CS125 FALL 2013 MP. File: Person.java, CS125 Project: Challenge6-RecursionSee, Version: 2013-11-05T16:32:01-0600.619512029
/**
 * @author dwoskin2
 *
 */
public class Person
{
private final String name;
private final int age;
private final char gender;
private final Person child1; //left child
private final Person child2; //right child

public Person(String name, int age, char gender, Person c1, Person c2)
{
    this.name=name;
    this.age=age;
    this.gender=gender;
    this.child1 = c1;
    this.child2 = c2;
}

public String toString()
{
    return name+"*"+age+"*"+gender;
}

public String getName() 
{
	return name;
}

public int getAge() 
{
	return age;
}

public char getGender() 
{
	return gender;
}

public boolean equals(Person p)
{
	return (this.name.equals(p.name)) &&
			(this.age==p.age) &&
			(this.gender==p.gender);
}


public void print() 
{
   System.out.println(this);
   if(child1 != null)
       child1.print();
   if(child2 != null)
       child2.print();
   
}

public int count(){ // total person count including this object
	int count = 1;
	if (child1 != null)
		count+=child1.count();
	if (child2 != null)
		count+=child2.count();
	return count;
}
public int countGrandChildren(){ // but not greatGrandChildren
	
	return grandchildren(0);
//	int count = 0;
//	if (child1 != null)
//		count+=child1.countGrandChildren();
//	if (child2 != null)
//		count+=child2.countGrandChildren();
//	return count;
}
public int grandchildren(int generation){
	int count = 0;
	if (generation == 2)
		return 1; 
	if (child1 != null)
		count += child1.grandchildren(generation + 1);
	if (child2 != null)
		count += child2.grandchildren(generation + 1);
	return count;
}

public int countMaxGenerations(){
	int oldest = 1;
	if (child1 != null){
		oldest = Math.max(child1.countMaxGenerations() + 1, oldest);
	}
	if (child2 != null){
		oldest = Math.max(child2.countMaxGenerations() + 1, oldest);
	}
	return oldest;
}

public int countGender(char gen){
	int count;
	if (gen == this.gender)
		count = 1;
	else count = 0;
	if (child1 != null){
		count += child1.countGender(gen);
	}
	if (child2 != null){
		count += child2.countGender(gen);
	}
	return count;
}


public Person search(String name, int maxGeneration){
	Person p;
	if (maxGeneration >= 0){
		if (name == this.name) 
			return this;
		if (child1 != null){
			p = child1.search(name, maxGeneration - 1);
			if (p != null)
				return p;
		}
		if (child2 != null){
			p = child2.search(name, maxGeneration - 1);
			if (p != null)
				return p;
		}
		return null;
	}
	return null;
}

} // end of class
