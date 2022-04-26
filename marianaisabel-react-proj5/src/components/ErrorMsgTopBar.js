import React, { useEffect } from "react"
import { connect } from 'react-redux'
import{
HStack,
Text,
Flex
} from "@chakra-ui/react";
import { FormattedMessage ,useIntl} from "react-intl";

//vamos descontruir o props, e retirar o titulo e valor, e manter os restantes caracteristicas do props no props
function ErrorMsg ({error="",...props}) {
    const intl = useIntl();
    console.log("dentro do componente do erro "+ error);
    const MsgErro =()=>{
        if(error && error!=null){
            return ( <Text > {intl.formatMessage({
                id: error,
                defaultMessage: "" + error
            })} </Text>)
        }else {
            return ( <Text > {error} </Text>)
        }
    } 
    useEffect(() => {
        window.scrollTo(0, 0)


    }, [error])
   
    return (
        <Flex background={"red.600"} 
        color={"white"} 
        fontWeight="bold"
        justifyContent={"center"}
        >
            
            <MsgErro  />
            

        </Flex>
    );
        
}

const mapStateToProps = state => {
    return { error: state.errorMsg.error };
  };

export default connect(mapStateToProps,{})(ErrorMsg);