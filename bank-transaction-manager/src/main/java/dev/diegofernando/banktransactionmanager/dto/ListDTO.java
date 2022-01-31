package dev.diegofernando.banktransactionmanager.dto;

import java.util.List;

public class ListDTO <T> {
    private List<T> data;
    private int size;
    private int page;

    public ListDTO(List<T> data, int size, int page) {
        this.data = data;
        this.size = size;
        this.page = page;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
