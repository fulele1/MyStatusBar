package mag.com.xaqb.fireprotectionmag;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ComQueryActivity extends BaseActivity {
    private ComQueryActivity instance;
    private Button btn_query;
    private EditText et_brand;
    private EditText et_org;
    private EditText et_com;


    @Override
    public void initTitle() {
        setTitleB("企业查询");
    }

    @Override
    public void initView() {
        instance = this;
        setStatusColorB(instance.getResources().getColor(R.color.wirte), View.VISIBLE,true);
        setContentView(R.layout.activity_com_query);
        btn_query = (Button) findViewById(R.id.btn_query_cq);
        et_brand = (EditText) findViewById(R.id.txt_brand_cq);
        et_org = (EditText) findViewById(R.id.txt_org_cq);
        et_com = (EditText) findViewById(R.id.et_com_cq);
    }

    @Override
    public void initAvailable() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void addEvent() {
        btn_query.setOnClickListener(instance);
        et_brand.setOnClickListener(instance);
        et_org.setOnClickListener(instance);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_query_cq://查询
//                intentB(instance,ComListActivity.class);
                query();
                break;
            case R.id.txt_brand_cq://选择品牌
                Intent intentBrand = new Intent(instance,SearchBrandActivity.class);
                startActivityForResult(intentBrand,0);
                break;
            case R.id.txt_org_cq://选择管辖机构
                Intent intentOrg = new Intent(instance,SearchOrgActivity.class);
                startActivityForResult(intentOrg,1);
                break;
        }
    }

    private void query() {
        String brand = et_brand.getText().toString();
        String org = et_org.getText().toString();
        String com = et_com.getText().toString();
        intentB(instance,ComListActivity.class,"brand",brand,"org",org,"com",com);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode ==RESULT_OK){
            Bundle bundle = data.getExtras();
            if (bundle != null){
                String result = bundle.getString("coms");
                String code = bundle.getString("code");
                et_brand.setText(code+"-"+result);
            }else if (requestCode==1 && resultCode==RESULT_OK){
                if (bundle != null) {
                    String result = bundle.getString("soname");
                    String code = bundle.getString("socode");
                    et_org.setText(code+"-"+result);
                }
            }
        }
    }
}
