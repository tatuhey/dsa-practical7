public class DSAHashTable {
    private Object m_value;
    private String m_key;
    private int m_state;

    public DSAHashTable(Object val, String key, int sta) {
        m_value = val;
        m_key = key;
        m_state = sta; // 0 = free, 1 = used, 2 = previously-used
    }

    //region mutator

    //endregion
    //region accessor

    //endregion
}