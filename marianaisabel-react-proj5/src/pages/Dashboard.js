import React from 'react'
import { useState, useEffect, } from "react";
//Redirect replace by Naviagate
import {Navigate, useParams, useNavigate} from 'react-router-dom'
//https://react-hook-form.com/
import { useForm } from "react-hook-form";
import {FormattedMessage ,useIntl} from "react-intl";
import {
    Flex,
    Heading,
    Text,
    Box,

} from "@chakra-ui/react";
import useFetch from 'use-http';
import { connect } from 'react-redux'
import ErrorMsgTopBar from '../components/ErrorMsgTopBar'
import DashboardCard from "../components/DashboardCard"
import{connectWSGeneralDashboard} from "../api_websocket"

//TODO: 
function setAppError(error){
    console.log(error)
}

//  * @param {*} updatetUsersList corresponde à função da acção que guarda o valor do numero de utilizadores registados


   /**
 * Dashboard é a camada que aloja os nossos outros componentes.
 * Nesta função estamos a estabelecer ligação ao websocket geral, e enviar a informação os nossos componentes por props, através do uso do estado local (useState)
 * Estamos a guardar no reducer a informção sobre o número de utilizadores, pois teremos elementos à escuta de alterações neste valor (lista de utilizadores)
 * @param {*} error corresponde à informação que vem do reducer sobre informação se existe ou não um erro, sobre a qual se faz a logica de exibir ou não o componente
 * @param {*} ...props restantes props que possam ser passadas
* @returns 
 */
function Dashboard({errorTopBar,...props}) {
    //const { get, post, del, response, loading, error } = useFetch();
    const intl = useIntl();
    let navigate = useNavigate();


    /**** *******************************************STATE******************************************************** */

    const [isAdmin, setAdminPriv]=useState(false); // is logged user an admin?
    const [restResponse, setRestResponse]=useState(""); //OK or NOK or ""
    const [scrollDown, setScrollDown]=useState(false)

    /**** ****************************************FORM*********************************************************** */
    //fuções que chamos ao submeter o formulário de edição
    const {register, handleSubmit, formState: {errors}}= useForm();
    async function onSubmit (data, e) {
    }
     /**** ******************************************USE EFFECT********************************************************* */



    const [totalUsers, setTotalUsers]=useState("--");
    const [totalNews, setTotalNews]=useState("--");
    const [totalProjects, setTotalProjects]=useState("--");
    const [totalKeywords, setTotalKeywords]=useState("--");

    
    console.log(errorTopBar);
    useEffect(()=>{
        connectWSGeneralDashboard((evt)=>{
            console.log("info do ws do dashboard geral" + evt.data)

            console.log(JSON.parse(evt.data));
            let stats =JSON.parse(evt.data);
            setTotalUsers( stats.TotalUsers);
            setTotalKeywords( stats.TotalKeywords);
            setTotalProjects( stats.TotalProjects);
            setTotalNews( stats.TotalNews);
        // setAvgActiv ( parseFloat(stats.AvgActivPerUser).toFixed(2)); 
    

            // updatetUsersList(stats.TotalUsers);

        });
    },[]);
    

/**** ******************************************RENDER / RETURN PRINCIPAL ********************************************************* */

    return (
    <Box>
          <Flex
        flexDirection="column"
        width="100wh"
        minHeight="83vh"
        backgroundColor="gray.200"
        justifyContent="center"
        alignItems="center"
        >

        <Heading><FormattedMessage id={"dashboard"} /> </Heading>
  
        {errorTopBar && 'Error!'}
        {errorTopBar && errorTopBar!=""
        ? <ErrorMsgTopBar />
        : null
        }

            <Box >
                <DashboardCard title={intl.formatMessage({id: 'tt_users'})} value={totalUsers}/> 
                <DashboardCard title={intl.formatMessage({id: 'tt_news'})} value={totalNews}/> 
                <DashboardCard title={intl.formatMessage({id: 'tt_projects'})} value={totalProjects}/> 
                <DashboardCard title={intl.formatMessage({id: 'tt_keywords'})} value={totalKeywords}/> 
            </Box> 

            {/* <Box >
                <Box >
                    <UserFilter />
                </Box>
                <Box >
                    <UserCards /> 
                </Box > 
            </Box> */}
            {/* <Box >
                <PieChart activitiesPieChart={activitiesPieChart} />
                <LineChart usersPerDay={usersPerDay}/>
            </Box>
        </Box> */}

        {restResponse=="OK"?
        <Text my={5} color="green"><FormattedMessage id={"sucess_on_updating"} /> </Text>
        : restResponse=="NOK"?
        <Text my={5} color="red"> <FormattedMessage id={"error_on_updating"} />  </Text>
        :null
        }
        {/* <Box mb={30}>
            <RedirectButton path="/about" description={intl.formatMessage({id: "_back_to_team" })} />
        </Box > */}
        {/* <Avatar name={register.firstName & " " & register.lastName} src={register.image} /> */}
        </Flex>
    </Box>
    );
}

const mapStateToProps = state => {
    return { userPriv: state.loginOK.userPriv,
        errorTopBar: state.errorMsg.errorTopBar
    };
};

export default connect(mapStateToProps, {})(Dashboard);

