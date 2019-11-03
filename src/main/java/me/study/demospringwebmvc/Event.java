package me.study.demospringwebmvc;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class Event {

    interface ValidateLimit {}
    interface ValidateName {}

    private Integer id;
    @NotBlank(groups = ValidateName.class)
    private String naem;
    @Min(value = 0, groups = ValidateLimit.class)
    private Integer limit;

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNaem() {
        return naem;
    }

    public void setNaem(String naem) {
        this.naem = naem;
    }
}
