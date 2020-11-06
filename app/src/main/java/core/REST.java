/** munter - das mobile unterrichtsbegleitende Unterstützungssystem für angehende Lehrpersonen
© 2020 Herrmann Elfreich

This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, on
    any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
**/

package core;

import com.squareup.moshi.Json;
import com.squareup.moshi.Moshi;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/* import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST; */

public class REST {

 /*   public static void rest() throws IOException {

        Procedure data = getProcedure();

        Moshi moshi = new Moshi.Builder().build();

        // if you want to see the json that will be generated ...
        //String json = moshi.adapter(Procedure.class).indent("  ").toJson(data);
        //System.out.println(json);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8675/")
                .addConverterFactory(MoshiConverterFactory.create(moshi)) // com.squareup.retrofit2:converter-moshi:latest.version
                .build();

        MyApi api = retrofit.create(MyApi.class);

        Response<Void> resp = api.postProcedure(data).execute();
        if (resp.isSuccessful())
            System.out.println("Success!");
    }

    /**
     * helper method to create a 'Procedure' object
     */
/*    private static Procedure getProcedure() {
        Procedure proc = new Procedure();
        proc.name = "BR_SP_BRN_GET_STORE_ACTIVE_SHIPMENTS";

        proc.parameters = new ArrayList<>();
        proc.parameters.add(new Parameter("StoreCode", "B201"));
        return proc;
    }

    // ------------------------------------------------------------------------------------------
// DTO classes for json serialization.  Do better validation / immutability when for realsies
// ------------------------------------------------------------------------------------------
    static class Procedure {
        @Json(name = "ProcName") String name;
        @Json(name = "Parameters") List<Parameter> parameters;
    }

    static class Parameter {
        @Json(name = "Name") String name;
        @Json(name = "Value") String value;

        public Parameter(String name, String value) {
            this.name = name;
            this.value = value;
        }
    }

    // ------------------------------------------------------------------------------------------
// Retrofit Api
// ------------------------------------------------------------------------------------------
    interface MyApi {
        @POST("my-endpoint")
        Call<Void> postProcedure(@Body Procedure procedure);
    } */
}
