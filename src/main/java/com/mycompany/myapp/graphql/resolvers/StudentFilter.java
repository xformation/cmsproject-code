package com.mycompany.myapp.graphql.resolvers;

import com.fasterxml.jackson.annotation.JsonProperty;



import javax.persistence.Query;
import java.util.Map;
import java.util.Optional;

/**
 * @author Xiangbin HAN (hanxb2001@163.com)
 *
 */
public class StudentFilter {

    private String sName;
   

    @JsonProperty("sName")
    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }
   
    @Override
    public String toString() {
        return "StudentFilter{} " + super.toString();
    }

    /**
     * @return String
     */
   
    /**
     * @param query
     */
    public void buildJpaQueryParameters(Query query) {
        
        Optional<StudentFilter> nonNullFilter = Optional.ofNullable(this);
        
        nonNullFilter.map(f -> f.getsName()).ifPresent(item -> query.setParameter("sName", item + "%"));
        
        
    }

    public String buildJpaQuery() {
        Optional<StudentFilter> nonNullFilter = Optional.ofNullable(this);
        StringBuilder sb = new StringBuilder(" WHERE");
        sb.append(
            nonNullFilter.map(f -> f.getsName())
                .map(item -> " student.sName LIKE :sName and")
                .orElse("")
        );
        
        if(sb.indexOf(" and") > 0)
            return sb.substring(0, sb.lastIndexOf(" and"));
        else
            return "";
        
    }
    
}
