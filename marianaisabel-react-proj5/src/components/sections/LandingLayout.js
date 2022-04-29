import React from "react"
import { Flex } from "@chakra-ui/react"
import Header from "../sections/Header"
import Footer from "../sections/Footer" // will add this in the part 2

export default function LandingLayout(props) {
  return (
    <Flex 
      direction="column"
      align="center"
      maxW={{ xl: "1200px" }}
      m="0 auto"
      {...props}
    >
      <Header />
      {props.children}
      <Footer />
    </Flex>
  )
}


// import React from 'react'
// import { useState, useEffect, } from "react";
// //Redirect replace by Naviagate
// import {Navigate, useParams, useNavigate} from 'react-router-dom'
// //https://react-hook-form.com/
// import { useForm } from "react-hook-form";
// import {FormattedMessage ,useIntl} from "react-intl";

// import {
//     Flex,
//     Heading,
//     Input,
//     Button,
//     InputGroup,
//     Stack,
//     Box,
//     Link,
//     Select,
//     Avatar,
//     Badge,
//     FormControl,
//     FormHelperText,
//     FormErrorMessage,
//     FormLabel,
//     Text,
//     Spacer,
//     Textarea,
//     Tooltip,
//     Editable,
//     EditableInput,
//     EditableTextarea,
//     EditablePreview,
//     ButtonGroup,
//     IconButton,
//     useEditableControls,
//     InputRightElement
// } from "@chakra-ui/react";

// import useFetch from 'use-http';
// import { connect } from 'react-redux'
// import RedirectButton from "../components/RedirectButton"

// //TODO: 
// function setAppError(error){
//     console.log(error)
// }

// function UserManagement({userPriv, ...props}) {
//     const { get, post, del, response, loading, error } = useFetch();
//     const intl = useIntl();
//     const navigate = useNavigate();
//     const { id } = useParams();
//     console.log(id)


//     /**** *******************************************STATE******************************************************** */

//     const [isAdmin, setAdminPriv]=useState(false); // is logged user an admin?
//     const [restResponse, setRestResponse]=useState(""); //OK or NOK or ""
//     const [scrollDown, setScrollDown]=useState(false)

//     /**** ****************************************FORM*********************************************************** */
//     //fuções que chamos ao submeter o formulário de edição
//     const {register, handleSubmit, formState: {errors}}= useForm();
//     async function onSubmit (data, e) {
//     }
//      /**** ******************************************USE EFFECT********************************************************* */

//     useEffect(async()=>{
//         console.log("dentro do userEffect");
//         setRestResponse("");

//         //const xxxx = await get("xxx")
//         if (response.ok) {
//            // console.log(xxx)
//            // setXX(xxx);
//             if(userPriv =="ADMIN"){
//                 setAdminPriv(true);
//             }
//             setAppError("");
//         } else if(response.status==401) {
//             console.log("credenciais erradas? " + error)
//             setRestResponse("NOK");
//             setAppError('error_fetch_login_401');
//         }else{
//             console.log("houve um erro no fetch " + error)
//             if(error && error!=""){
//                 setAppError(  error );
//                 setRestResponse("NOK");
//             }else{
//                 setAppError(  "error_fetch_generic" );
//                 setRestResponse("NOK");
//             }
//         }
//     },[])

// /**** ******************************************RENDER / RETURN PRINCIPAL ********************************************************* */

//     return (
//     <Box>
//           <Flex
//         flexDirection="column"
//         width="100wh"
//         minHeight="83vh"
//         backgroundColor="gray.200"
//         justifyContent="center"
//         alignItems="center"
//         >

//         <Heading><FormattedMessage id={"user_mangement"} /> </Heading>

//         {error && 'Error!'}
//         {loading && 'Loading...'}


//         {restResponse=="OK"?
//         <Text my={5} color="green"><FormattedMessage id={"sucess_on_updating"} /> </Text>
//         : restResponse=="NOK"?
//         <Text my={5} color="red"> <FormattedMessage id={"error_on_updating"} />  </Text>
//         :null
//         }
//         {/* <Box mb={30}>
//             <RedirectButton path="/about" description={intl.formatMessage({id: "_back_to_team" })} />
//         </Box > */}
//         {/* <Avatar name={register.firstName & " " & register.lastName} src={register.image} /> */}
//         </Flex>
//     </Box>
//     );
// }

// const mapStateToProps = state => {
//     return { userPriv: state.loginOK.userPriv,
//             errorTopBar: state.errorMsg.errorTopBar
//     };
// };

// export default connect(mapStateToProps,{})(UserManagement)

