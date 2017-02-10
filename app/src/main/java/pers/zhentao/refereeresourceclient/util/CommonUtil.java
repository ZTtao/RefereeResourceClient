package pers.zhentao.refereeresourceclient.util;

import android.text.SpannableString;

import java.util.HashMap;

import pers.zhentao.refereeresourceclient.R;

/**
 * Created by ZhangZT on 2016/7/21 22:28.
 * E-mail: 327502540@qq.com
 * Project: RefereeResource
 */
public class CommonUtil {
    private static CommonUtil commonUtil = null;
    private CommonUtil(){}
    public static CommonUtil getInstance(){
        if(commonUtil==null){
            synchronized (CommonUtil.class){
                if(commonUtil==null){
                    commonUtil = new CommonUtil();
                }
            }
        }
        return commonUtil;
    }
    public HashMap<String,Object> findMicroExpressionById(int id){
        HashMap<String,Object> map = new HashMap<>();
        if(id == R.id.micro_expression_fa){
            map.put("resource", R.mipmap.micro001);
            map.put("spannableString",new SpannableString("[/micro001]"));
        }else if(id == R.id.micro_expression_fb){
            map.put("resource",R.mipmap.micro002);
            map.put("spannableString",new SpannableString("[/micro002]"));
        }else if(id == R.id.micro_expression_fc){
            map.put("resource",R.mipmap.micro003);
            map.put("spannableString",new SpannableString("[/micro003]"));
        }else if(id == R.id.micro_expression_fd){
            map.put("resource",R.mipmap.micro004);
            map.put("spannableString",new SpannableString("[/micro004]"));
        }else if(id == R.id.micro_expression_fe){
            map.put("resource",R.mipmap.micro005);
            map.put("spannableString",new SpannableString("[/micro005]"));
        }else if(id == R.id.micro_expression_ff){
            map.put("resource",R.mipmap.micro006);
            map.put("spannableString",new SpannableString("[/micro006]"));
        }else if(id == R.id.micro_expression_fg){
            map.put("resource",R.mipmap.micro007);
            map.put("spannableString",new SpannableString("[/micro007]"));
        }else if(id == R.id.micro_expression_fh){
            map.put("resource",R.mipmap.micro008);
            map.put("spannableString",new SpannableString("[/micro008]"));
        }else if(id == R.id.micro_expression_fi){
            map.put("resource",R.mipmap.micro009);
            map.put("spannableString",new SpannableString("[/micro009]"));
        }else if(id == R.id.micro_expression_fj){
            map.put("resource",R.mipmap.micro010);
            map.put("spannableString",new SpannableString("[/micro010]"));
        }else if(id == R.id.micro_expression_fk){
            map.put("resource",R.mipmap.micro011);
            map.put("spannableString",new SpannableString("[/micro011]"));
        }else if(id == R.id.micro_expression_fl){
            map.put("resource",R.mipmap.micro012);
            map.put("spannableString",new SpannableString("[/micro012]"));
        }else if(id == R.id.micro_expression_fm){
            map.put("resource",R.mipmap.micro013);
            map.put("spannableString",new SpannableString("[/micro013]"));
        }else if(id == R.id.micro_expression_fn){
            map.put("resource",R.mipmap.micro014);
            map.put("spannableString",new SpannableString("[/micro014]"));
        }else if(id == R.id.micro_expression_fo){
            map.put("resource",R.mipmap.micro015);
            map.put("spannableString",new SpannableString("[/micro015]"));
        }else if(id == R.id.micro_expression_fp){
            map.put("resource",R.mipmap.micro016);
            map.put("spannableString",new SpannableString("[/micro016]"));
        }else if(id == R.id.micro_expression_fq){
            map.put("resource",R.mipmap.micro017);
            map.put("spannableString",new SpannableString("[/micro017]"));
        }else if(id == R.id.micro_expression_fr){
            map.put("resource",R.mipmap.micro018);
            map.put("spannableString",new SpannableString("[/micro018]"));
        }else if(id == R.id.micro_expression_fs){
            map.put("resource",R.mipmap.micro019);
            map.put("spannableString",new SpannableString("[/micro019]"));
        }else if(id == R.id.micro_expression_ft){
            map.put("resource",R.mipmap.micro020);
            map.put("spannableString",new SpannableString("[/micro020]"));
        }else if(id == R.id.micro_expression_sa){
            map.put("resource",R.mipmap.micro021);
            map.put("spannableString",new SpannableString("[/micro021]"));
        }else if(id == R.id.micro_expression_sb){
            map.put("resource",R.mipmap.micro022);
            map.put("spannableString",new SpannableString("[/micro022]"));
        }else if(id == R.id.micro_expression_sc){
            map.put("resource",R.mipmap.micro023);
            map.put("spannableString",new SpannableString("[/micro023]"));
        }else if(id == R.id.micro_expression_sd){
            map.put("resource",R.mipmap.micro024);
            map.put("spannableString",new SpannableString("[/micro024]"));
        }else if(id == R.id.micro_expression_se){
            map.put("resource",R.mipmap.micro025);
            map.put("spannableString",new SpannableString("[/micro025]"));
        }else if(id == R.id.micro_expression_sf){
            map.put("resource",R.mipmap.micro026);
            map.put("spannableString",new SpannableString("[/micro026]"));
        }else if(id == R.id.micro_expression_sg){
            map.put("resource",R.mipmap.micro027);
            map.put("spannableString",new SpannableString("[/micro027]"));
        }else if(id == R.id.micro_expression_sh){
            map.put("resource",R.mipmap.micro028);
            map.put("spannableString",new SpannableString("[/micro028]"));
        }else if(id == R.id.micro_expression_si){
            map.put("resource",R.mipmap.micro029);
            map.put("spannableString",new SpannableString("[/micro029]"));
        }else if(id == R.id.micro_expression_sj){
            map.put("resource",R.mipmap.micro030);
            map.put("spannableString",new SpannableString("[/micro030]"));
        }else if(id == R.id.micro_expression_sk){
            map.put("resource",R.mipmap.micro031);
            map.put("spannableString",new SpannableString("[/micro031]"));
        }else if(id == R.id.micro_expression_sl){
            map.put("resource",R.mipmap.micro032);
            map.put("spannableString",new SpannableString("[/micro032]"));
        }else if(id == R.id.micro_expression_sm){
            map.put("resource",R.mipmap.micro033);
            map.put("spannableString",new SpannableString("[/micro033]"));
        }else if(id == R.id.micro_expression_sn){
            map.put("resource",R.mipmap.micro034);
            map.put("spannableString",new SpannableString("[/micro034]"));
        }else if(id == R.id.micro_expression_so){
            map.put("resource",R.mipmap.micro035);
            map.put("spannableString",new SpannableString("[/micro035]"));
        }else if(id == R.id.micro_expression_sp){
            map.put("resource",R.mipmap.micro036);
            map.put("spannableString",new SpannableString("[/micro036]"));
        }else if(id == R.id.micro_expression_sq){
            map.put("resource",R.mipmap.micro037);
            map.put("spannableString",new SpannableString("[/micro037]"));
        }else if(id == R.id.micro_expression_sr){
            map.put("resource",R.mipmap.micro038);
            map.put("spannableString",new SpannableString("[/micro038]"));
        }else if(id == R.id.micro_expression_ss){
            map.put("resource",R.mipmap.micro039);
            map.put("spannableString",new SpannableString("[/micro039]"));
        }else if(id == R.id.micro_expression_st){
            map.put("resource",R.mipmap.micro040);
            map.put("spannableString",new SpannableString("[/micro040]"));
        }else if(id == R.id.micro_expression_ta){
            map.put("resource",R.mipmap.micro041);
            map.put("spannableString",new SpannableString("[/micro041]"));
        }else if(id == R.id.micro_expression_tb){
            map.put("resource",R.mipmap.micro042);
            map.put("spannableString",new SpannableString("[/micro042]"));
        }else if(id == R.id.micro_expression_tc){
            map.put("resource",R.mipmap.micro043);
            map.put("spannableString",new SpannableString("[/micro043]"));
        }else if(id == R.id.micro_expression_td){
            map.put("resource",R.mipmap.micro044);
            map.put("spannableString",new SpannableString("[/micro044]"));
        }else if(id == R.id.micro_expression_te){
            map.put("resource",R.mipmap.micro045);
            map.put("spannableString",new SpannableString("[/micro045]"));
        }else if(id == R.id.micro_expression_tf){
            map.put("resource",R.mipmap.micro046);
            map.put("spannableString",new SpannableString("[/micro046]"));
        }else if(id == R.id.micro_expression_tg){
            map.put("resource",R.mipmap.micro047);
            map.put("spannableString",new SpannableString("[/micro047]"));
        }else if(id == R.id.micro_expression_th){
            map.put("resource",R.mipmap.micro048);
            map.put("spannableString",new SpannableString("[/micro048]"));
        }else if(id == R.id.micro_expression_ti){
            map.put("resource",R.mipmap.micro049);
            map.put("spannableString",new SpannableString("[/micro049]"));
        }else if(id == R.id.micro_expression_tj){
            map.put("resource",R.mipmap.micro050);
            map.put("spannableString",new SpannableString("[/micro050]"));
        }else if(id == R.id.micro_expression_tk){
            map.put("resource",R.mipmap.micro051);
            map.put("spannableString",new SpannableString("[/micro051]"));
        }else if(id == R.id.micro_expression_tl){
            map.put("resource",R.mipmap.micro052);
            map.put("spannableString",new SpannableString("[/micro052]"));
        }else if(id == R.id.micro_expression_tm){
            map.put("resource",R.mipmap.micro053);
            map.put("spannableString",new SpannableString("[/micro053]"));
        }else if(id == R.id.micro_expression_tn){
            map.put("resource",R.mipmap.micro054);
            map.put("spannableString",new SpannableString("[/micro054]"));
        }else if(id == R.id.micro_expression_to){
            map.put("resource",R.mipmap.micro055);
            map.put("spannableString",new SpannableString("[/micro055]"));
        }else if(id == R.id.micro_expression_tp){
            map.put("resource",R.mipmap.micro056);
            map.put("spannableString",new SpannableString("[/micro056]"));
        }else if(id == R.id.micro_expression_tq){
            map.put("resource",R.mipmap.micro057);
            map.put("spannableString",new SpannableString("[/micro057]"));
        }else if(id == R.id.micro_expression_tr){
            map.put("resource",R.mipmap.micro058);
            map.put("spannableString",new SpannableString("[/micro058]"));
        }else if(id == R.id.micro_expression_ts){
            map.put("resource",R.mipmap.micro059);
            map.put("spannableString",new SpannableString("[/micro059]"));
        }else if(id == R.id.micro_expression_tt){
            map.put("resource",R.mipmap.micro060);
            map.put("spannableString",new SpannableString("[/micro060]"));
        }else if(id == R.id.micro_expression_fra){
            map.put("resource",R.mipmap.micro061);
            map.put("spannableString",new SpannableString("[/micro061]"));
        }else if(id == R.id.micro_expression_frb){
            map.put("resource",R.mipmap.micro062);
            map.put("spannableString",new SpannableString("[/micro062]"));
        }else if(id == R.id.micro_expression_frc){
            map.put("resource",R.mipmap.micro063);
            map.put("spannableString",new SpannableString("[/micro063]"));
        }else if(id == R.id.micro_expression_frd){
            map.put("resource",R.mipmap.micro064);
            map.put("spannableString",new SpannableString("[/micro064]"));
        }else if(id == R.id.micro_expression_fre){
            map.put("resource",R.mipmap.micro065);
            map.put("spannableString",new SpannableString("[/micro065]"));
        }else if(id == R.id.micro_expression_frf){
            map.put("resource",R.mipmap.micro066);
            map.put("spannableString",new SpannableString("[/micro066]"));
        }else if(id == R.id.micro_expression_frg){
            map.put("resource",R.mipmap.micro067);
            map.put("spannableString",new SpannableString("[/micro067]"));
        }else if(id == R.id.micro_expression_frh){
            map.put("resource",R.mipmap.micro068);
            map.put("spannableString",new SpannableString("[/micro068]"));
        }else if(id == R.id.micro_expression_fri){
            map.put("resource",R.mipmap.micro069);
            map.put("spannableString",new SpannableString("[/micro069]"));
        }else if(id == R.id.micro_expression_frj){
            map.put("resource",R.mipmap.micro070);
            map.put("spannableString",new SpannableString("[/micro070]"));
        }else if(id == R.id.micro_expression_frk){
            map.put("resource",R.mipmap.micro071);
            map.put("spannableString",new SpannableString("[/micro071]"));
        }else if(id == R.id.micro_expression_frl){
            map.put("resource",R.mipmap.micro072);
            map.put("spannableString",new SpannableString("[/micro072]"));
        }else if(id == R.id.micro_expression_frm){
            map.put("resource",R.mipmap.micro073);
            map.put("spannableString",new SpannableString("[/micro073]"));
        }else if(id == R.id.micro_expression_frn){
            map.put("resource",R.mipmap.micro074);
            map.put("spannableString",new SpannableString("[/micro074]"));
        }else if(id == R.id.micro_expression_fro){
            map.put("resource",R.mipmap.micro075);
            map.put("spannableString",new SpannableString("[/micro075]"));
        }else if(id == R.id.micro_expression_frp){
            map.put("resource",R.mipmap.micro076);
            map.put("spannableString",new SpannableString("[/micro076]"));
        }else if(id == R.id.micro_expression_frq){
            map.put("resource",R.mipmap.micro077);
            map.put("spannableString",new SpannableString("[/micro077]"));
        }else if(id == R.id.micro_expression_frr){
            map.put("resource",R.mipmap.micro078);
            map.put("spannableString",new SpannableString("[/micro078]"));
        }else if(id == R.id.micro_expression_frs){
            map.put("resource",R.mipmap.micro079);
            map.put("spannableString",new SpannableString("[/micro079]"));
        }else if(id == R.id.micro_expression_frt){
            map.put("resource",R.mipmap.micro080);
            map.put("spannableString",new SpannableString("[/micro080]"));
        }
        return map;
    }

    public HashMap<String,Object> findMicroExpressionByTag(int id){
        HashMap<String,Object> map = new HashMap<>();
        if(id == 1){
            map.put("resource",R.mipmap.micro001);
            map.put("spannableString",new SpannableString("[/micro001]"));
        }else if(id == 2){
            map.put("resource",R.mipmap.micro002);
            map.put("spannableString",new SpannableString("[/micro002]"));
        }else if(id == 3){
            map.put("resource",R.mipmap.micro003);
            map.put("spannableString",new SpannableString("[/micro003]"));
        }else if(id == 4){
            map.put("resource",R.mipmap.micro004);
            map.put("spannableString",new SpannableString("[/micro004]"));
        }else if(id == 5){
            map.put("resource",R.mipmap.micro005);
            map.put("spannableString",new SpannableString("[/micro005]"));
        }else if(id == 6){
            map.put("resource",R.mipmap.micro006);
            map.put("spannableString",new SpannableString("[/micro006]"));
        }else if(id == 7){
            map.put("resource",R.mipmap.micro007);
            map.put("spannableString",new SpannableString("[/micro007]"));
        }else if(id == 8){
            map.put("resource",R.mipmap.micro008);
            map.put("spannableString",new SpannableString("[/micro008]"));
        }else if(id == 9){
            map.put("resource",R.mipmap.micro009);
            map.put("spannableString",new SpannableString("[/micro009]"));
        }else if(id == 10){
            map.put("resource",R.mipmap.micro010);
            map.put("spannableString",new SpannableString("[/micro010]"));
        }else if(id == 11){
            map.put("resource",R.mipmap.micro011);
            map.put("spannableString",new SpannableString("[/micro011]"));
        }else if(id == 12){
            map.put("resource",R.mipmap.micro012);
            map.put("spannableString",new SpannableString("[/micro012]"));
        }else if(id == 13){
            map.put("resource",R.mipmap.micro013);
            map.put("spannableString",new SpannableString("[/micro013]"));
        }else if(id == 14){
            map.put("resource",R.mipmap.micro014);
            map.put("spannableString",new SpannableString("[/micro014]"));
        }else if(id == 15){
            map.put("resource",R.mipmap.micro015);
            map.put("spannableString",new SpannableString("[/micro015]"));
        }else if(id == 16){
            map.put("resource",R.mipmap.micro016);
            map.put("spannableString",new SpannableString("[/micro016]"));
        }else if(id == 17){
            map.put("resource",R.mipmap.micro017);
            map.put("spannableString",new SpannableString("[/micro017]"));
        }else if(id == 18){
            map.put("resource",R.mipmap.micro018);
            map.put("spannableString",new SpannableString("[/micro018]"));
        }else if(id == 19){
            map.put("resource",R.mipmap.micro019);
            map.put("spannableString",new SpannableString("[/micro019]"));
        }else if(id == 20){
            map.put("resource",R.mipmap.micro020);
            map.put("spannableString",new SpannableString("[/micro020]"));
        }else if(id == 21){
            map.put("resource",R.mipmap.micro021);
            map.put("spannableString",new SpannableString("[/micro021]"));
        }else if(id == 22){
            map.put("resource",R.mipmap.micro022);
            map.put("spannableString",new SpannableString("[/micro022]"));
        }else if(id == 23){
            map.put("resource",R.mipmap.micro023);
            map.put("spannableString",new SpannableString("[/micro023]"));
        }else if(id == 24){
            map.put("resource",R.mipmap.micro024);
            map.put("spannableString",new SpannableString("[/micro024]"));
        }else if(id == 25){
            map.put("resource",R.mipmap.micro025);
            map.put("spannableString",new SpannableString("[/micro025]"));
        }else if(id == 26){
            map.put("resource",R.mipmap.micro026);
            map.put("spannableString",new SpannableString("[/micro026]"));
        }else if(id == 27){
            map.put("resource",R.mipmap.micro027);
            map.put("spannableString",new SpannableString("[/micro027]"));
        }else if(id == 28){
            map.put("resource",R.mipmap.micro028);
            map.put("spannableString",new SpannableString("[/micro028]"));
        }else if(id == 29){
            map.put("resource",R.mipmap.micro029);
            map.put("spannableString",new SpannableString("[/micro029]"));
        }else if(id == 30){
            map.put("resource",R.mipmap.micro030);
            map.put("spannableString",new SpannableString("[/micro030]"));
        }else if(id == 31){
            map.put("resource",R.mipmap.micro031);
            map.put("spannableString",new SpannableString("[/micro031]"));
        }else if(id == 32){
            map.put("resource",R.mipmap.micro032);
            map.put("spannableString",new SpannableString("[/micro032]"));
        }else if(id == 33){
            map.put("resource",R.mipmap.micro033);
            map.put("spannableString",new SpannableString("[/micro033]"));
        }else if(id == 34){
            map.put("resource",R.mipmap.micro034);
            map.put("spannableString",new SpannableString("[/micro034]"));
        }else if(id == 35){
            map.put("resource",R.mipmap.micro035);
            map.put("spannableString",new SpannableString("[/micro035]"));
        }else if(id == 36){
            map.put("resource",R.mipmap.micro036);
            map.put("spannableString",new SpannableString("[/micro036]"));
        }else if(id == 37){
            map.put("resource",R.mipmap.micro037);
            map.put("spannableString",new SpannableString("[/micro037]"));
        }else if(id == 38){
            map.put("resource",R.mipmap.micro038);
            map.put("spannableString",new SpannableString("[/micro038]"));
        }else if(id == 39){
            map.put("resource",R.mipmap.micro039);
            map.put("spannableString",new SpannableString("[/micro039]"));
        }else if(id == 40){
            map.put("resource",R.mipmap.micro040);
            map.put("spannableString",new SpannableString("[/micro040]"));
        }else if(id == 41){
            map.put("resource",R.mipmap.micro041);
            map.put("spannableString",new SpannableString("[/micro041]"));
        }else if(id == 42){
            map.put("resource",R.mipmap.micro042);
            map.put("spannableString",new SpannableString("[/micro042]"));
        }else if(id == 43){
            map.put("resource",R.mipmap.micro043);
            map.put("spannableString",new SpannableString("[/micro043]"));
        }else if(id == 44){
            map.put("resource",R.mipmap.micro044);
            map.put("spannableString",new SpannableString("[/micro044]"));
        }else if(id == 45){
            map.put("resource",R.mipmap.micro045);
            map.put("spannableString",new SpannableString("[/micro045]"));
        }else if(id == 46){
            map.put("resource",R.mipmap.micro046);
            map.put("spannableString",new SpannableString("[/micro046]"));
        }else if(id == 47){
            map.put("resource",R.mipmap.micro047);
            map.put("spannableString",new SpannableString("[/micro047]"));
        }else if(id == 48){
            map.put("resource",R.mipmap.micro048);
            map.put("spannableString",new SpannableString("[/micro048]"));
        }else if(id == 49){
            map.put("resource",R.mipmap.micro049);
            map.put("spannableString",new SpannableString("[/micro049]"));
        }else if(id == 50){
            map.put("resource",R.mipmap.micro050);
            map.put("spannableString",new SpannableString("[/micro050]"));
        }else if(id == 51){
            map.put("resource",R.mipmap.micro051);
            map.put("spannableString",new SpannableString("[/micro051]"));
        }else if(id == 52){
            map.put("resource",R.mipmap.micro052);
            map.put("spannableString",new SpannableString("[/micro052]"));
        }else if(id == 53){
            map.put("resource",R.mipmap.micro053);
            map.put("spannableString",new SpannableString("[/micro053]"));
        }else if(id == 54){
            map.put("resource",R.mipmap.micro054);
            map.put("spannableString",new SpannableString("[/micro054]"));
        }else if(id == 55){
            map.put("resource",R.mipmap.micro055);
            map.put("spannableString",new SpannableString("[/micro055]"));
        }else if(id == 56){
            map.put("resource",R.mipmap.micro056);
            map.put("spannableString",new SpannableString("[/micro056]"));
        }else if(id == 57){
            map.put("resource",R.mipmap.micro057);
            map.put("spannableString",new SpannableString("[/micro057]"));
        }else if(id == 58){
            map.put("resource",R.mipmap.micro058);
            map.put("spannableString",new SpannableString("[/micro058]"));
        }else if(id == 59){
            map.put("resource",R.mipmap.micro059);
            map.put("spannableString",new SpannableString("[/micro059]"));
        }else if(id == 60){
            map.put("resource",R.mipmap.micro060);
            map.put("spannableString",new SpannableString("[/micro060]"));
        }else if(id == 61){
            map.put("resource",R.mipmap.micro061);
            map.put("spannableString",new SpannableString("[/micro061]"));
        }else if(id == 62){
            map.put("resource",R.mipmap.micro062);
            map.put("spannableString",new SpannableString("[/micro062]"));
        }else if(id == 63){
            map.put("resource",R.mipmap.micro063);
            map.put("spannableString",new SpannableString("[/micro063]"));
        }else if(id == 64){
            map.put("resource",R.mipmap.micro064);
            map.put("spannableString",new SpannableString("[/micro064]"));
        }else if(id == 65){
            map.put("resource",R.mipmap.micro065);
            map.put("spannableString",new SpannableString("[/micro065]"));
        }else if(id == 66){
            map.put("resource",R.mipmap.micro066);
            map.put("spannableString",new SpannableString("[/micro066]"));
        }else if(id == 67){
            map.put("resource",R.mipmap.micro067);
            map.put("spannableString",new SpannableString("[/micro067]"));
        }else if(id == 68){
            map.put("resource",R.mipmap.micro068);
            map.put("spannableString",new SpannableString("[/micro068]"));
        }else if(id == 69){
            map.put("resource",R.mipmap.micro069);
            map.put("spannableString",new SpannableString("[/micro069]"));
        }else if(id == 70){
            map.put("resource",R.mipmap.micro070);
            map.put("spannableString",new SpannableString("[/micro070]"));
        }else if(id == 71){
            map.put("resource",R.mipmap.micro071);
            map.put("spannableString",new SpannableString("[/micro071]"));
        }else if(id == 72){
            map.put("resource",R.mipmap.micro072);
            map.put("spannableString",new SpannableString("[/micro072]"));
        }else if(id == 73){
            map.put("resource",R.mipmap.micro073);
            map.put("spannableString",new SpannableString("[/micro073]"));
        }else if(id == 74){
            map.put("resource",R.mipmap.micro074);
            map.put("spannableString",new SpannableString("[/micro074]"));
        }else if(id == 75){
            map.put("resource",R.mipmap.micro075);
            map.put("spannableString",new SpannableString("[/micro075]"));
        }else if(id == 76){
            map.put("resource",R.mipmap.micro076);
            map.put("spannableString",new SpannableString("[/micro076]"));
        }else if(id == 77){
            map.put("resource",R.mipmap.micro077);
            map.put("spannableString",new SpannableString("[/micro077]"));
        }else if(id == 78){
            map.put("resource",R.mipmap.micro078);
            map.put("spannableString",new SpannableString("[/micro078]"));
        }else if(id == 79){
            map.put("resource",R.mipmap.micro079);
            map.put("spannableString",new SpannableString("[/micro079]"));
        }else if(id == 80){
            map.put("resource",R.mipmap.micro080);
            map.put("spannableString",new SpannableString("[/micro080]"));
        }
        return map;
    }
}
