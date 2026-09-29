public class DSAHashEntry {
    private Object m_value;
    private String m_key;
    private int m_state; // 0 = free, 1 = used, 2 = previously-used

    public DSAHashEntry(Object val, String key) {
        m_value = val;
        m_key = key;
    }

    //region accessor
    public Object getValue() {
        return m_value;
    }

    public String getKey() {
        return m_key;
    }

    public int getState() {
        return m_state;
    }
    //endregion

    //region mutator
    // 0 = free, 1 = used, 2 = previously-used
    public void setFreeState() {
        m_state = 0;
    }

    public void setUsedState() {
        m_state = 1;
    }

    public void setPrevUsedState() {
        m_state = 2;
    }

    public void setValue(Object val) {
        m_value = val;
    }

    public void setKey(String key) {
        m_key = key;
    }

    //endregion

}