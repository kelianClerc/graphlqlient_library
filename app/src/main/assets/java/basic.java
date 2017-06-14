import java.util.Collection;

/*
{
    users {
    id,
    name
    }
}
*/

public class Query {
    Collection<Field> fields();
}

public class Field {
    String name();
    Collection<Field> fields();
}
