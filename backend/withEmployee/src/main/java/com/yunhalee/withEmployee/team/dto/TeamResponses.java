package com.yunhalee.withEmployee.team.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TeamResponses {

    private Long totalElement;
    private Integer totalPage;
    private List<TeamResponse> teams;

    public TeamResponses(Long totalElement, Integer totalPage, List<TeamResponse> teams) {
        this.totalElement = totalElement;
        this.totalPage = totalPage;
        this.teams = teams;
    }

    public static TeamResponses of(Long totalElement, Integer totalPage, List<TeamResponse> teams) {
        return new TeamResponses(totalElement, totalPage, teams);
    }
}
