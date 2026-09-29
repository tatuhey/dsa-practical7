

public class DSAHashEntry {
    private Object m_value;
    private String m_key;
    private int m_state; // 0 = free, 1 = used, -1 = previously-used

    //region constructor
    public DSAHashEntry(Object val, String key) {
        m_value = val;
        m_key = key;
        m_state = 1;
    }

    public DSAHashEntry() {
        m_value  = null;
        m_key = "";
        m_state = 0;
    }
    //endregion

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

    @Override
    public String toString() {
        return "Key: " + m_key + ", State: " + m_state + "Value: " + m_value;
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
        m_state = -1;
    }

    public void setValue(Object val) {
        m_value = val;
    }

    public void setKey(String key) {
        m_key = key;
    }

    public void setAll(Object val, String key) {
        setValue(val);
        setKey(key);
        setUsedState();
    }

    public void delAll() {
        setValue(null);
        setKey("");
        setPrevUsedState();
    }

    //endregion

}