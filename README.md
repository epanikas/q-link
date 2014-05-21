=q-Link=

Briefly speaking, q-Link is a Java collection manipulation library that was made in an attempt to unify the way collections are treated, disregarding the original source of the data. For example, searching for a certain element in a collection in q-Link would look absolutely the same for a list stored in memory, and for a table stored in DB.

As an inspiration the SQL was taken, which, so far, does a fairly good job at describing what we want to achieve from a set of data. So basically q-Link is a DSL that allows you to describe the collection manipulation process.

==Hands on==

You might be asking yourself why the heck do we need another sql-like library to work with collections? Well, strictly speaking we don't. However, the following examples may increase your motivation to read on:

{{{
List<Employee> employees = ...;
}}}


  * filter a list of employees by job title
{{{
List<Employee> result = sf.forList(employees).filter().p("job").eq().val("guru").toList();
}}}

  * order a list of employees by age (and then by salary)
{{{
List<Employee> result = sf.forList(employees).order().by("age").toList();
List<Employee> result = sf.forList(employees).order().by("age").by("salary").toList();
}}}

  * transform a list by selecting a field
{{{
List<Integer> result = sf.forList(employees).select().p("age", Integer.class).toList();
}}}

  * visit each element in a collection to set a value to a field
{{{
sf.forList(employees).visit().p("bonus").assign().val(12000).toList();
}}}

  * sum up all the elements in the list
{{{
int sum = sf.forList(Arrays.asList(1,2,3,4,5)).aggregate().sum().toValue();
}}}

  * find min and max of the list
{{{
Pair<Integer, Integer> minMax = sf.forList(Arrays.asList(1,2,3,4,5)).aggregate().min().max().toValue();
}}}

  * extract the list of names of employees and their jobs
{{{
Pair<String, String> nameJobList = sf.forList(employees).select().p("name", String.class).p("job", String.class).toList();
}}}


  * group the employees by the job
{{{
Map<String, List<Employee>> jobGroup = sf.forList(employees).group().by("job", String.class).toMap();
}}}

  * for each job find the minimum and maximum of the salary
{{{
Map<String, Pair<Integer, Integer>> jobGroup =
sf.forList(employees).group().by("job", String.class)
.selectAs().minOf("salary", Integer.class).maxOf("salary", Integer.class).toMap();
}}}

All the previous examples are related to a list of employees stocked in memory. However, it would look the same, if we had to work with the table EMPLOYEES from the DB. In this case instead of specifying the list directly, we would specify the mapping class we are interested in.

{{{
List<Employee> result = hf.selectForClass(Employee.class).filter().p("job").eq().val("guru").toList();
}}}

The factory method has been changed, but the way the processing is described is the same.

As you can see, the q-Link organizes the various methods of collection manipulation in functional groups, each group providing the processing functions relevant to it. For example, filter() gives access to logical conditions, or even custom filter functions. Likewise, order() naturally will let you choose the sorting field, or again, will let you specify a custom ordering function. At any point, when you define a pipeline of processing, the only choices that are presented to you are those that are relevant to this stage. For example, the pipeline 
{{{
filter().toList() 
}}}
would not compile, since the filter definition is incomplete.

Since the q-Link aims at minimizing dependencies as much as possible, no code generation is involved whatsoever. However, as a result, the fields and their types must be specified in a manual way. There is a remedy for that, by using a so-called typed property.

Typed property is simply a pair of name-type. Once defined, it can be used wherever a definition of a property is needed. For example, in q-Link DSL p("job", String.class) designates a property of an object with the name "job" and of the type String. So instead of specifying each time name and class, a typed property can be defined, and used instead of name. 

Here is an example:
{{{
class Employee {
    private String job;
    public static class Tp {
        public static final TProperty<String> job = TPropertyImpl.forTypedName("job", String.class);
    }
}

List<Employee> gurus = sf.forList(employees).filter().p(Employee.Tp.job).eq().val("guru").toList(); 
}}}

Many more examples can be found in the unit tests of q-Link, especially in DB and memory modules.

==Installing q-Link==

Well, good news, you are still reading. So probably the next step would be to isntall q-Link and start playing with it by yourself. Well, that's easy. Follow this link for details: [InstallQLink installation]


==A glimpse of implementation==

The q-Link consists of 4 modules: api, mem, db, and common.

The current implementation of the library includes two implementations of the DSL: for memory and for DB. 

When the processing is started, a so-called processing definition is created, which is supposed to accumulate all the different processing states that were defined throughout the pipeline. Once one of the terminal methods - toList(), toMap(), toValue() - are called, this definition is passed to the processing engine, that would actually perform the processing, depending on the source of the collection - memory or DB.

===q-Link :: API===

The q-Link api defines the set of interfaces that together form the collection manipulation DSL. At this point no implementation is involved. The unit tests of this module include only the different test cases of the q-Link library.

This module also contains certain auxiliary classes, like Pair, that are needed to define the api. All these classes are immutable.

===q-Link :: Core===

This module contains the implementation of the DSL interfaces, that are common to memory and DB. The implementation of the actual processing is delegated to the relevant processing module: memory or DB. 

===q-Link :: In memory===

This module contains the processing implementation for the collections in memory. The processing of the collections in memory is never done in-place. Instead of modifying the original collection, the latter left intact, and a new collection is created and filled in with the processed elements. 

===q-Link :: Hibernate===

Finally, the db module defines the implementation of the DSL as far as DB is concerned. The DB implementation currently is based on the hibernate library, and as such requires the hibernate related dependencies.
