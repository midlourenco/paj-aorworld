import React, { useEffect } from "react"
import { connect } from 'react-redux'
import{
HStack,
Text,
Flex
} from "@chakra-ui/react";
import { FormattedMessage ,useIntl} from "react-intl";

//vamos descontruir o props, e retirar o titulo e valor, e manter os restantes caracteristicas do props no props
function ErrorMsg ({errorTopBar="",...props}) {
    const intl = useIntl();
    console.log("dentro do componente do erro "+ errorTopBar);
    const MsgErro =()=>{
        if(errorTopBar && errorTopBar!=null){
            return ( <Text > {intl.formatMessage({
                id: errorTopBar,
                defaultMessage: "" + errorTopBar
            })} </Text>)
        }else {
            return ( <Text > {errorTopBar} </Text>)
        }
    } 
    useEffect(() => {
        window.scrollTo(0, 0)


    }, [errorTopBar])
   
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
    return { errorTopBar: state.errorMsg.errorTopBar };
  };

export default connect(mapStateToProps,{})(ErrorMsg);