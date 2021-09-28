import React from 'react'
import {useHistory} from 'react-router-dom'

function SearchCompanyCard({id, name, ceo, setSearchCompany}) {
    const history = useHistory()

    const handleOnClick = () =>{
        setSearchCompany([])
        history.push(`/company/${id}`)
        setSearchCompany([])
    }
    return (
        <div className="search-company">
            <div onClick={handleOnClick} style={{cursor:"pointer"}}>{name}</div>
            <div style={{backgroundColor:"#fcdd88", color:"white", borderRadius:"3px"}}>&nbsp;{ceo}&nbsp;</div>
        </div>
    )
}

export default SearchCompanyCard
