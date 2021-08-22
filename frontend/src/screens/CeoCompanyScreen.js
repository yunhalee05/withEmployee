import React, { useEffect } from 'react'
import { useDispatch } from 'react-redux'
import { getcompanies } from '../_actions/companyActions'

function CeoCompanyScreen(props) {

    const id = props.match.params.id

    const dispatch = useDispatch()

    useEffect(() => {
       dispatch(getcompanies({id}))
    }, [dispatch])
    
    return (
        <div>
            
        </div>
    )
}

export default CeoCompanyScreen
