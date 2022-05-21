package com.yunhalee.withEmployee.team.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SimpleTeamResponses {

    private List<SimpleTeamResponse> teams;

    public SimpleTeamResponses(List<SimpleTeamResponse> teams) {
        this.teams = teams;
    }

    public static SimpleTeamResponses of(List<SimpleTeamResponse> teams) {
        return new SimpleTeamResponses(teams);
    }
}
