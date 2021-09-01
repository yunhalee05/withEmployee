import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { getteam } from '../_actions/teamActions'
import UserCard from '../components/UserCard'
import AddMemberModal from '../components/AddMemberModal'
import MessageCard from '../components/messages/MessageCard'
import ConversationCard from '../components/messages/ConversationCard'


function TeamScreen(props) {

    const id = props.match.params.id

    const dispatch = useDispatch()

    const [ceos, setCeos] = useState([])
    const [leaders, setLeaders] = useState([])
    const [members, setMembers] = useState([])

    const [addMember, setAddMember] = useState(false)

    const [conversation, setConversation] = useState({})

    useEffect(() => {
        dispatch(getteam({id})).then(res=>{
            setCeos(res.users.filter(user=>user.role==="CEO"))
            setLeaders(res.users.filter(user=>user.role==="Leader"))
            setMembers(res.users.filter(user=>user.role==="Member"))
        })

    }, [dispatch])

    const team = useSelector(state => state.team)


    return (
        <div className="user-team" style={{display:"flex", alignItems:'flex-start', justifyContent:"space-between", flexWrap:"wrap", maxWidth:"90rem"}}>
            {
                (ceos.length ===0 && leaders.length===0 && members.length===0) &&
                <div>
                    No Members yet.
                </div>
            }
            {
                team.loading ===false &&
                    <div className="user-card-container " style={{width:"32%"}}  >
                    {
                        ceos.map((user, index)=>(
                            <UserCard user={user} teamId={id} key={index} setCeos={setCeos} setLeaders={setLeaders} setMembers={setMembers} />
                        ))
                        
                    }
                    {
                        leaders.map((user, index)=>(
                            <UserCard user={user} teamId={id} key={index} setCeos={setCeos} setLeaders={setLeaders} setMembers={setMembers} />
                        ))
                        
                    }
                    {
                        members.map((user, index)=>(
                            <UserCard user={user} teamId={id} key={index} setCeos={setCeos} setLeaders={setLeaders} setMembers={setMembers} />
                        ))
                        
                    }
                        <div className="card-button">
                            <button onClick={()=>setAddMember(!addMember)}>ADD Member</button>
                        </div>
                    </div>
            }


            {
                (team.loading ===false && team.team) &&
                <div className="messages">
                    <ConversationCard users={team.team.users} setConversation={setConversation} conversation={conversation}/>
                    <MessageCard conversation={conversation} setConversation={setConversation}  />
                </div>
            }

            {
                addMember && 
                <AddMemberModal members={team.team.users} setAddMember={setAddMember} id={id} setCeos={setCeos} setLeaders={setLeaders} setMembers={setMembers} />

            }
        </div>
    )
}

export default TeamScreen
