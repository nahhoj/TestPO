package com.ec.kfc.pi.module;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.ejb.Local;
import javax.ejb.LocalHome;
import javax.ejb.Remote;
import javax.ejb.RemoteHome;
import javax.ejb.Stateless;

import com.sap.aii.af.lib.mp.module.Module;
import com.sap.aii.af.lib.mp.module.ModuleContext;
import com.sap.aii.af.lib.mp.module.ModuleData;
import com.sap.aii.af.lib.mp.module.ModuleException;
import com.sap.aii.af.lib.mp.module.ModuleHome;
import com.sap.aii.af.lib.mp.module.ModuleLocal;
import com.sap.aii.af.lib.mp.module.ModuleLocalHome;
import com.sap.aii.af.lib.mp.module.ModuleRemote;
import com.sap.engine.interfaces.messaging.api.Message;
import com.sap.engine.interfaces.messaging.api.exception.InvalidParamException;
import com.sap.tc.logging.Location;


/**
 * Session Bean implementation class ModuleTest
 */
@Stateless(name = "ModuleTest")
@Local(value = { ModuleLocal.class })
@Remote(value = { ModuleRemote.class })
@LocalHome(value = ModuleLocalHome.class)
@RemoteHome(value = ModuleHome.class)
public class ModuleTest implements Module{
	
	
	private static final Location logger = Location.getLocation(ModuleTest.class);

    /**
     * Default constructor. 
     */
    public ModuleTest() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public ModuleData process(ModuleContext arg0, ModuleData arg1) throws ModuleException {
		logger.infoT("Inicio Module");
		String param1=arg0.getContextData("param1");
		logger.debugT(param1);
		Message msg = (Message) arg1.getPrincipalData();
		try {
			String xml=inputStreamtoString(msg.getMainPayload().getInputStream());
			logger.infoT(xml);
			msg.getMainPayload().setContent(xml.getBytes());
		} catch (IOException | InvalidParamException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		logger.infoT("Fin Module");
		return arg1;
	}
	
	
	private String inputStreamtoString(InputStream is) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line;
		String xml;
		for (xml = ""; (line = br.readLine()) != null; xml = (new StringBuilder(String.valueOf(xml))).append(line)
				.append("\n").toString())
			;
		br.close();
		return xml;
	}
}
