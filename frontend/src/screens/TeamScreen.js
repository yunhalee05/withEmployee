import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { getteam } from '../_actions/teamActions'
import UserCard from '../components/UserCard'
import AddMemberModal from '../components/AddMemberModal'
import MessageCard from '../components/messages/MessageCard'
import ConversationCard from '../components/messages/ConversationCard'
import Loading from '../components/Loading'


function TeamScreen(props) {

    const id = props.match.params.id

    const dispatch = useDispatch()

    const auth = useSelector(state => state.auth)

    const [ceos, setCeos] = useState([])
    const [leaders, setLeaders] = useState([])
    const [members, setMembers] = useState([])

    const [addMember, setAddMember] = useState(false)

    const [conversation, setConversation] = useState({})

    useEffect(() => {
        dispatch(getteam({id})).then(res=>{      
            if(auth.user.teams.filter(t=>t.id===res.id).length>0 || auth.user.companies.filter(c=>c.id ===res.companyId).length>0){          
            setCeos(res.users.filter(user=>user.role==="CEO"))
            setLeaders(res.users.filter(user=>user.role==="Leader"))
            setMembers(res.users.filter(user=>user.role==="Member"))
            }else{
                props.history.goBack()
            }
        })
    }, [dispatch])

    const team = useSelector(state => state.team)


    return (
        <div className="user-team team-screen" >
            {
                team.loading && <Loading/>
            }

            {
                (team.loading ===false && team.team) &&
                <div className="team-messages" >
                    <ConversationCard users={team.team.users} setConversation={setConversation} conversation={conversation} belongTo="Team"/>
                    <MessageCard conversation={conversation} setConversation={setConversation}  />
                </div>
            }

            {
                (ceos.length ===0 && leaders.length===0 && members.length===0) &&
                <div>
                    No Members yet.
                </div>
            }
            {
                team.loading ===false &&
                <div className="user-card-container" >
                    {
                        ceos.map((user, index)=>(
                            <UserCard user={user} teamId={id} key={index} setCeos={setCeos} setLeaders={setLeaders} setMembers={setMembers} ceos={ceos} leaders={leaders} members={members}/>
                        ))
                    }
                    {
                        leaders.map((user, index)=>(
                            <UserCard user={user} teamId={id} key={index} setCeos={setCeos} setLeaders={setLeaders} setMembers={setMembers} ceos={ceos} leaders={leaders} members={members}/>
                        ))
                    }
                    {
                        members.map((user, index)=>(
                            <UserCard user={user} teamId={id} key={index} setCeos={setCeos} setLeaders={setLeaders} setMembers={setMembers} ceos={ceos} leaders={leaders} members={members}/>
                        ))
                    }
                    {
                        (auth.user.role==="CEO" || auth.user.role==="Leader") &&
                        <div className="card-button" onClick={()=>setAddMember(!addMember)}>
                            <i class="fas fa-user-plus fa-3x"></i>
                            <div style={{fontSize:'1.2rem', fontWeight:"800"}}>ADD Member</div> 
                        </div>
                    }
                </div>
            }
            {
                addMember && 
                <AddMemberModal members={team.team.users} setAddMember={setAddMember} id={id} setCeos={setCeos} setLeaders={setLeaders} setMembers={setMembers} ceos={ceos} leaders={leaders} member={members} />

            }
        </div>
    )
}

export default TeamScreen
